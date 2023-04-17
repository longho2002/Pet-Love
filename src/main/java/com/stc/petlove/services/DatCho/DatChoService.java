package com.stc.petlove.services.DatCho;

import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.DatChoRepository;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DatChoService implements IDatChoService {
    private final DatChoRepository datChoRepository;
    public DatChoService(DatChoRepository datChoRepository) {
        this.datChoRepository = datChoRepository;
    }
    @Async
    @Override
    public CompletableFuture<DatCho> create(DatChoDto input) {
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
}
