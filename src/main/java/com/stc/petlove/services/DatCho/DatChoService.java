package com.stc.petlove.services.DatCho;

import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.embedded.ThongTinDatCho;
import com.stc.petlove.exceptions.BadRequestException;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.DatChoRepository;
import com.stc.petlove.repositories.DichVuRepository;
import com.stc.petlove.utils.EnumTrangThaiDatCho;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
public class DatChoService implements IDatChoService {
    private final DatChoRepository datChoRepository;
    private final DichVuRepository dichVuRepository;
    private final MongoTemplate mongoTemplate;

    public DatChoService(DatChoRepository datChoRepository, DichVuRepository dichVuRepository, MongoTemplate mongoTemplate) {
        this.datChoRepository = datChoRepository;
        this.dichVuRepository = dichVuRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Async
    @Override
    public CompletableFuture<DatCho> create(DatChoDto input) {
        for (ThongTinDatCho data : input.getThongTinDatChos()) {
            DichVu dichVu = dichVuRepository.findByMaDichVu(data.getDichVu()).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        }
        DatCho dc = new DatCho();

        MapperUtils.toDto(input, dc);
        dc = datChoRepository.save(dc);
        return CompletableFuture.completedFuture(dc);
    }

    @Async

    @Override
    public CompletableFuture<DatCho> getOne(String id) {
        DatCho dc = datChoRepository.findById(id).orElse(null);
        if (dc == null) {
            throw new NotFoundException("Không tìm thấy đặt chỗ");
        }
        return CompletableFuture.completedFuture(dc);
    }

    @Async

    @Override
    public CompletableFuture<List<DatCho>> getAll() {
        return CompletableFuture.completedFuture(datChoRepository.findAll());

    }

    @Async

    @Override
    public CompletableFuture<DatCho> update(String id, DatCho data) {
        for (ThongTinDatCho x : data.getThongTinDatChos()) {
            DichVu dichVu = dichVuRepository.findByMaDichVu(x.getDichVu()).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        }
        DatCho dc = datChoRepository.findById(id).orElse(null);
        if (dc == null) {
            throw new NotFoundException("Không tìm thấy đặt chỗ");
        }
        MapperUtils.toDto(data, dc);
        data = datChoRepository.save(data);
        return CompletableFuture.completedFuture(data);
    }

    @Async

    @Override
    public CompletableFuture<Void> remove(String id) {
        DatCho dc = datChoRepository.findById(id).orElse(null);
        if (dc == null) {
            throw new NotFoundException("Không tìm thấy đặt chỗ");
        }
        dc.setTrangThai(false);
        datChoRepository.save(dc);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<DatCho> setTrangThai(String id, boolean trangThai) {
        DatCho dc = datChoRepository.findById(id).orElse(null);
        if (dc == null) {
            throw new NotFoundException("Không tìm thấy đặt chỗ");
        }
        dc.setTrangThai(trangThai);
        dc = datChoRepository.save(dc);
        return CompletableFuture.completedFuture(dc);
    }

    @Async
    @Override
    public CompletableFuture<List<DatCho>> findDatChoWithPaginationAndSearch(long skip, int limit, String name, String orderBy) {
//       return CompletableFuture.completedFuture(datChoRepository.findDatChoWithPaginationAndSearch(skip, limit, name, orderBy));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("email").regex(Pattern.compile(name, Pattern.CASE_INSENSITIVE))),
                Aggregation.sort(orderBy.contains("-") ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy.contains("-") ? orderBy.substring(0, orderBy.indexOf('-')) : orderBy),
                Aggregation.skip(skip),
                Aggregation.limit(limit)
        );
        return CompletableFuture.completedFuture(mongoTemplate.aggregate(aggregation, "dat-cho", DatCho.class).getMappedResults());
    }

    @Async
    @Override
    public CompletableFuture<Long> countDatCho(String name) {
        return CompletableFuture.supplyAsync(() -> datChoRepository.countDatCho(name).orElse(0L));

    }

    @Async
    @Override
    public CompletableFuture<DatCho> updateTrangThai(String id, int status) {
        DatCho dc = datChoRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy đặt chỗ"));
        switch (status) {
            case 0: {
                dc.setTrangThaiDatCho(EnumTrangThaiDatCho.DANG_THUC_HIEN.name());
                break;
            }
            case 1: {
                dc.setTrangThaiDatCho(EnumTrangThaiDatCho.HOAN_THANH.name());
                break;
            }
            case 2: {
                if (dc.getTrangThaiDatCho().equals(EnumTrangThaiDatCho.DAT_CHO.name()))
                    dc.setTrangThaiDatCho(EnumTrangThaiDatCho.HUY.name());
                else
                    throw new BadRequestException("Không thể hủy đặt chỗ");
                break;
            }
        }
        dc = datChoRepository.save(dc);
        return CompletableFuture.completedFuture(dc);
    }
}
