package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.dtos.PagedResultDto;
import com.stc.petlove.dtos.Pagination;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.services.LoaiThuCung.ILoaiThuCungService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@ApiPrefixController("/loaithucung")
public class LoaiThuCungController {
    private final ILoaiThuCungService loaiThuCungService;

    public LoaiThuCungController(ILoaiThuCungService loaiThuCungService) {
        this.loaiThuCungService = loaiThuCungService;
    }

    @PostMapping(value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<LoaiThuCung> create(LoaiThuCungDto input) {
        return loaiThuCungService.create(input);
    }

    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<LoaiThuCung> getOne(@PathVariable(value = "id") String id) {
        return loaiThuCungService.getOne(id);

    }

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<List<LoaiThuCung>> getAll() {
        return loaiThuCungService.getAll();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<LoaiThuCung> update(@PathVariable(value = "id") String id, @RequestBody LoaiThuCung data) {
        return loaiThuCungService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return loaiThuCungService.remove(id);
    }

    @PatchMapping("/setTrangThai/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<LoaiThuCung> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name = "trangthai") boolean trangThai) {
        return loaiThuCungService.setTrangThai(id, trangThai);
    }

    @GetMapping("/findPaginate")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PagedResultDto<LoaiThuCung> findDatChoWithPaginationAndSearch(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size,
                                                                      @RequestParam(name = "content", defaultValue = "") String name,
                                                                         @RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {
        CompletableFuture<Long> total = loaiThuCungService.countLoaiThuCung(name);
        CompletableFuture<List<LoaiThuCung>> tks = loaiThuCungService.findLoaiThuCungWithPaginationAndSearch((long) page * size, size, name, orderBy);
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
