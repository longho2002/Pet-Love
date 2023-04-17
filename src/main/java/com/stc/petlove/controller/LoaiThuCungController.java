package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.LoaiThuCung.ILoaiThuCungService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@RestController
@ApiPrefixController("/loaithucung")
public class LoaiThuCungController {
    private final ILoaiThuCungService loaiThuCungService;

    public LoaiThuCungController(ILoaiThuCungService loaiThuCungService) {
        this.loaiThuCungService = loaiThuCungService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<LoaiThuCung> create(LoaiThuCungDto input) {
        return loaiThuCungService.create(input);
    }

    @GetMapping(value = "/{id}")
    public CompletableFuture<LoaiThuCung> getOne(@PathVariable(value = "id") String id) {
        return loaiThuCungService.getOne(id);

    }

    @GetMapping()
    public CompletableFuture<List<LoaiThuCung>> getAll() {
        return loaiThuCungService.getAll();
    }

    @PatchMapping("/{id}")
    public CompletableFuture<LoaiThuCung> update(@PathVariable(value = "id") String id, @RequestBody LoaiThuCung data) {
        return loaiThuCungService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return loaiThuCungService.remove(id);
    }
    @PatchMapping("/setTrangThai/{id}")
    public CompletableFuture<LoaiThuCung> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name="trangthai") boolean trangThai)
    {
        return loaiThuCungService.setTrangThai(id, trangThai);
    }
}