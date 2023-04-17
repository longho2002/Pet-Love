package com.stc.petlove.services.LoaiThuCung;

import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.LoaiThuCungRepository;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LoaiThuCungService implements ILoaiThuCungService {
    private final LoaiThuCungRepository loaiThuCungRepository;

    public LoaiThuCungService(LoaiThuCungRepository loaiThuCungRepository) {
        this.loaiThuCungRepository = loaiThuCungRepository;
    }

    @Async
    @Override
    public CompletableFuture<LoaiThuCung> create(LoaiThuCungDto input) {
        LoaiThuCung ltc = new LoaiThuCung();
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
}
