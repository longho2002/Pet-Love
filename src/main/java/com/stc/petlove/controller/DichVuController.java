package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.dtos.PagedResultDto;
import com.stc.petlove.dtos.Pagination;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.embedded.GiaDichVu;
import com.stc.petlove.services.DichVu.IDichVuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@ApiPrefixController("/dichvu")
public class DichVuController {
    private final IDichVuService dichVuService;

    public DichVuController(IDichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    @PostMapping(value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<DichVu> create(DichVuDto input) {
        return dichVuService.create(input);
    }

    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<DichVu> getOne(@PathVariable(value = "id") String id) {
        return dichVuService.getOne(id);
    }

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<List<DichVu>> getAll() {
        return dichVuService.getAll();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<DichVu> update(@PathVariable(value = "id") String id, @RequestBody DichVu data) {
        return dichVuService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return dichVuService.remove(id);
    }

    @PatchMapping("/setTrangThai/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<DichVu> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name = "trangthai") boolean trangThai) {
        return dichVuService.setTrangThai(id, trangThai);
    }

    @GetMapping("/findPaginate")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PagedResultDto<DichVu> findDatChoWithPaginationAndSearch(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "10") int size,
                                                                    @RequestParam(name = "content", defaultValue = "") String name,
                                                                    @RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {
        CompletableFuture<Long> total = dichVuService.countDichVu(name);
        CompletableFuture<List<DichVu>> tks = dichVuService.findDichVuWithPaginationAndSearch((long) page * size, size, name, orderBy);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(total, tks);
        try {
            allFutures.get();
            return PagedResultDto.create(Pagination.create(total.get(), (long) page * size, size), tks.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Some thing went wrong!");
    }

    @GetMapping("/addGiaDichVu/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<DichVu> addGiaDichVu(@PathVariable(value = "id") String id, @RequestBody GiaDichVu input) {
        return dichVuService.addGiaDichVu(id, input);
    }
}
