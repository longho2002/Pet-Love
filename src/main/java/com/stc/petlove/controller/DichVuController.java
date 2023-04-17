package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.DichVu.IDichVuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@RestController
@ApiPrefixController("/dichvu")
public class DichVuController {
    private final IDichVuService dichVuService;

    public DichVuController(IDichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<DichVu> create(DichVuDto input) {
        return dichVuService.create(input);
    }

    @GetMapping(value = "/{id}")
    public CompletableFuture<DichVu> getOne(@PathVariable(value = "id") String id) {
        return dichVuService.getOne(id);

    }

    @GetMapping()
    public CompletableFuture<List<DichVu>> getAll() {
        return dichVuService.getAll();
    }

    @PatchMapping("/{id}")
    public CompletableFuture<DichVu> update(@PathVariable(value = "id") String id, @RequestBody DichVu data) {
        return dichVuService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return dichVuService.remove(id);
    }

    @PatchMapping("/setTrangThai/{id}")
    public CompletableFuture<DichVu> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name="trangthai") boolean trangThai)
    {
        return dichVuService.setTrangThai(id, trangThai);
    }
}
