package com.stc.petlove.services.DichVu;

import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.DichVuRepository;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DichVuService implements IDichVuService {
    private final DichVuRepository dichVuRepository;

    public DichVuService(DichVuRepository dichVuRepository) {
        this.dichVuRepository = dichVuRepository;
    }
    @Async
    @Override
    public CompletableFuture<DichVu> create(DichVuDto input) {
        DichVu dv = dichVuRepository.findByMaDichVu(input.getMaDichVu()).orElse(null);
        if (dv != null) {
            throw new NotFoundException("Mã dịch vụ đã tồn tại");
        }
        dv = new DichVu();
        MapperUtils.toDto(input, dv);
        dv = dichVuRepository.save(dv);
        return CompletableFuture.completedFuture(dv);
    }
    @Async
    @Override
    public CompletableFuture<DichVu> getOne(String id) {
        DichVu dv = dichVuRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        return CompletableFuture.completedFuture(dv);
    }
    @Async
    @Override
    public CompletableFuture<List<DichVu>> getAll() {
        return CompletableFuture.completedFuture(dichVuRepository.findAll());

    }
    @Async
    @Override
    public CompletableFuture<DichVu> update(String id, DichVu data) {
        DichVu dv = dichVuRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        DichVu checkDv = dichVuRepository.findByMaDichVu(data.getMaDichVu()).orElse(null);
        if (checkDv != null && !checkDv.getId().equals(id))
            throw new NotFoundException("Mã dịch vụ đã tồn tại");
        MapperUtils.toDto(data, dv);
        data = dichVuRepository.save(data);
        return CompletableFuture.completedFuture(data);
    }
    @Async
    @Override
    public CompletableFuture<Void> remove(String id) {
        DichVu dv = dichVuRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        dv.setTrangThai(false);
        dichVuRepository.save(dv);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<DichVu> setTrangThai(String id, boolean trangThai) {
        DichVu dv = dichVuRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        dv.setTrangThai(trangThai);
        dv = dichVuRepository.save(dv);
        return CompletableFuture.completedFuture(dv);
    }
}
