package com.stc.petlove.services.DichVu;

import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.embedded.GiaDichVu;
import com.stc.petlove.entities.embedded.ThongTinDatCho;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.DichVuRepository;
import com.stc.petlove.repositories.LoaiThuCungRepository;
import com.stc.petlove.utils.MapperUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DichVuService implements IDichVuService {
    private final DichVuRepository dichVuRepository;
    private final LoaiThuCungRepository loaiThuCungRepository;


    public DichVuService(DichVuRepository dichVuRepository, LoaiThuCungRepository loaiThuCungRepository) {
        this.dichVuRepository = dichVuRepository;
        this.loaiThuCungRepository = loaiThuCungRepository;
    }

    @Async
    @Override
    public CompletableFuture<DichVu> create(DichVuDto input) {
        for (GiaDichVu data : input.getGiaDichVus()) {
            dichVuRepository.findById(data.getLoaiThuCung()).orElseThrow(() -> new NotFoundException("Không tìm thấy loại thú cưng"));
        }
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
    public CompletableFuture<DichVu> update(String id, DichVu input) {
        for (GiaDichVu data : input.getGiaDichVus()) {
            dichVuRepository.findById(data.getLoaiThuCung()).orElseThrow(() -> new NotFoundException("Không tìm thấy loại thú cưng"));
        }
        DichVu dv = dichVuRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        DichVu checkDv = dichVuRepository.findByMaDichVu(input.getMaDichVu()).orElse(null);
        if (checkDv != null && !checkDv.getId().equals(id))
            throw new NotFoundException("Mã dịch vụ đã tồn tại");
        MapperUtils.toDto(input, dv);
        input = dichVuRepository.save(input);
        return CompletableFuture.completedFuture(input);
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

    @Async
    @Override
    public CompletableFuture<List<DichVu>> findDichVuWithPaginationAndSearch(long skip, int limit, String name) {
        return CompletableFuture.completedFuture(dichVuRepository.findDichVuWithPaginationAndSearch(skip, limit, name));
    }

    @Async
    @Override
    public CompletableFuture<Long> countDichVu(String name) {
        return CompletableFuture.supplyAsync(() -> dichVuRepository.countDichVu(name).orElse(0L));

    }

    @Override
    @Async
    public CompletableFuture<DichVu> addGiaDichVu(String id, GiaDichVu input) {
        DichVu dv = dichVuRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ"));
        LoaiThuCung ltc = loaiThuCungRepository.findById(input.getLoaiThuCung()).orElseThrow(() -> new NotFoundException("Không tìm thấy loại thú cưng"));
        dv.getGiaDichVus().add(input);
        dv =  dichVuRepository.save(dv);
        return CompletableFuture.completedFuture(dv);
    }
}
