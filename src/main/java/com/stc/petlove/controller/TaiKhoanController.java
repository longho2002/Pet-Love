package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.TaiKhoanDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.TaiKhoan.ITaiKhoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController("/taikhoan")
public class TaiKhoanController {
    private final ITaiKhoanService taiKhoanService;

    public TaiKhoanController(ITaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<TaiKhoan> create(@RequestBody TaiKhoanDto input) {
        return taiKhoanService.create(input);
    }

    @GetMapping(value = "/{id}")
    public CompletableFuture<TaiKhoan> getOne(@PathVariable(value = "id") String id) {
        return taiKhoanService.getOne(id);

    }

    @GetMapping()
    public CompletableFuture<List<TaiKhoan>> getAll() {
        return taiKhoanService.getAll();
    }

    @PatchMapping("/{id}")
    public CompletableFuture<TaiKhoan> update(@PathVariable(value = "id") String id, @RequestBody TaiKhoan data) {
        return taiKhoanService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return taiKhoanService.remove(id);
    }

    @PatchMapping("/setTrangThai/{id}")
    public CompletableFuture<TaiKhoan> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name = "trangthai") boolean trangThai) {
        return taiKhoanService.setTrangThai(id, trangThai);
    }
}
