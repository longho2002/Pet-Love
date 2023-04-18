package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.PagedResultDto;
import com.stc.petlove.dtos.Pagination;
import com.stc.petlove.dtos.TaiKhoanDto;
import com.stc.petlove.dtos.UpdateProfileDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.TaiKhoan.ITaiKhoanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<TaiKhoan> getOne(@PathVariable(value = "id") String id) {
        return taiKhoanService.getOne(id);

    }
    @GetMapping("/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    public CompletableFuture<TaiKhoan> getProfile() {
        return taiKhoanService.getProfile();

    }

    @PatchMapping("/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    public CompletableFuture<TaiKhoan> updateProfile(@RequestBody UpdateProfileDto data) {
        return taiKhoanService.updateProfile(data);
    }

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<List<TaiKhoan>> getAll() {
        return taiKhoanService.getAll();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<TaiKhoan> update(@PathVariable(value = "id") String id, @RequestBody TaiKhoan data) {
        return taiKhoanService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return taiKhoanService.remove(id);
    }

    @PatchMapping("/setTrangThai/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<TaiKhoan> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name = "trangthai") boolean trangThai) {
        return taiKhoanService.setTrangThai(id, trangThai);
    }


    @GetMapping("/findPaginate")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PagedResultDto<TaiKhoan> findTaiKhoanWithPaginationAndSearch(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                                        @RequestParam(name = "content", defaultValue = "") String name,
                                                                        @RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {
        CompletableFuture<Long> total = taiKhoanService.countTaiKhoan(name);
        CompletableFuture<List<TaiKhoan>> tks = taiKhoanService.findTaiKhoanWithPaginationAndSearch((long) page * size, size, name, orderBy);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(total, tks);
        try {
            allFutures.get();
            return PagedResultDto.create(Pagination.create(total.get(), (long) page * size, size), tks.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Some thing went wrong!");
    }
}
