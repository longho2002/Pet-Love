package com.stc.petlove.services.LoaiThuCung;

import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.LoaiThuCungRepository;
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
public class LoaiThuCungService implements ILoaiThuCungService {
    private final LoaiThuCungRepository loaiThuCungRepository;
    private final MongoTemplate mongoTemplate;
    public LoaiThuCungService(LoaiThuCungRepository loaiThuCungRepository, MongoTemplate mongoTemplate) {
        this.loaiThuCungRepository = loaiThuCungRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Async
    @Override
    public CompletableFuture<LoaiThuCung> create(LoaiThuCungDto input) {
        LoaiThuCung ltc = loaiThuCungRepository.findByMaLoaiThuCung(input.getMaLoaiThuCung()).orElse(null);
        if (ltc != null) {
            throw new NotFoundException("Mã loại thú cưng đã tồn tại");
        }
        ltc = new LoaiThuCung();
        MapperUtils.toDto(input, ltc);
        ltc = loaiThuCungRepository.save(ltc);
        return CompletableFuture.completedFuture(ltc);
    }

    @Async
    @Override
    public CompletableFuture<LoaiThuCung> getOne(String id) {
        LoaiThuCung ltc = loaiThuCungRepository.findById(id).orElse(null);
        if (ltc == null) {
            throw new NotFoundException("Không tìm thấy loại thú cưng");
        }
        return CompletableFuture.completedFuture(ltc);
    }

    @Async
    @Override
    public CompletableFuture<List<LoaiThuCung>> getAll() {
        return CompletableFuture.completedFuture(loaiThuCungRepository.findAll());
    }

    @Async
    @Override
    public CompletableFuture<LoaiThuCung> update(String id, LoaiThuCung data) {
        LoaiThuCung ltc = loaiThuCungRepository.findById(id).orElse(null);
        if (ltc == null) {
            throw new NotFoundException("Không tìm thấy loại thú cưng");
        }
        LoaiThuCung checkLtc = loaiThuCungRepository.findByMaLoaiThuCung(data.getMaLoaiThuCung()).orElse(null);
        if (checkLtc != null && !checkLtc.getId().equals(id)) {
            throw new NotFoundException("Mã loại thú cưng đã tồn tại");
        }
        MapperUtils.toDto(data, ltc);
        data = loaiThuCungRepository.save(data);
        return CompletableFuture.completedFuture(data);
    }

    @Async
    @Override
    public CompletableFuture<Void> remove(String id) {
        LoaiThuCung ltc = loaiThuCungRepository.findById(id).orElse(null);
        if (ltc == null) {
            throw new NotFoundException("Không tìm thấy loại thú cưng");
        }
        ltc.setTrangThai(false);
        loaiThuCungRepository.save(ltc);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<LoaiThuCung> setTrangThai(String id, boolean trangThai) {
        LoaiThuCung ltc = loaiThuCungRepository.findById(id).orElse(null);
        if (ltc == null) {
            throw new NotFoundException("Không tìm thấy loại thú cưng");
        }
        ltc.setTrangThai(trangThai);
        ltc = loaiThuCungRepository.save(ltc);
        return CompletableFuture.completedFuture(ltc);
    }

    @Async
    @Override
    public CompletableFuture<List<LoaiThuCung>> findLoaiThuCungWithPaginationAndSearch(long skip, int limit, String name, String orderBy) {
//        return CompletableFuture.completedFuture(loaiThuCungRepository.findLoaiThuCungWithPaginationAndSearch(skip, limit, name));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("tenLoaiThuCung").regex(Pattern.compile(name, Pattern.CASE_INSENSITIVE))),
                Aggregation.sort(orderBy.contains("-") ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy.contains("-") ? orderBy.substring(0, orderBy.indexOf('-')) : orderBy),
                Aggregation.skip(skip),
                Aggregation.limit(limit)
        );
        return CompletableFuture.completedFuture(mongoTemplate.aggregate(aggregation, "loai-thu-cung", LoaiThuCung.class).getMappedResults());
    }

    @Async
    @Override
    public CompletableFuture<Long> countLoaiThuCung(String name) {
        return CompletableFuture.supplyAsync(() -> loaiThuCungRepository.countLoaiThuCung(name).orElse(0L));

    }
}
