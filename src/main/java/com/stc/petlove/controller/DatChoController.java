package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.dtos.PagedResultDto;
import com.stc.petlove.dtos.Pagination;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.services.DatCho.IDatChoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@ApiPrefixController("/datcho")
public class DatChoController {
    private final IDatChoService datChoService;

    public DatChoController(IDatChoService datChoService) {
        this.datChoService = datChoService;
    }

    @PostMapping(value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")

    public CompletableFuture<DatCho> create(DatChoDto input) {
        return datChoService.create(input);
    }

    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public CompletableFuture<DatCho> getOne(@PathVariable(value = "id") String id) {
        return datChoService.getOne(id);

    }

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<List<DatCho>> getAll() {
        return datChoService.getAll();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")

    public CompletableFuture<DatCho> update(@PathVariable(value = "id") String id, @RequestBody DatCho data) {
        return datChoService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return datChoService.remove(id);
    }

    @PatchMapping("/updateTrangThai/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public CompletableFuture<DatCho> remove(@PathVariable(value = "id") String id, @RequestParam int trangThai) {
        return datChoService.updateTrangThai(id, trangThai);
    }


    @PatchMapping("/setTrangThai/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<DatCho> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name = "trangthai") boolean trangThai) {
        return datChoService.setTrangThai(id, trangThai);
    }

    @GetMapping("/findPaginate")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PagedResultDto<DatCho> findDatChoWithPaginationAndSearch(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "10") int size,
                                                                    @RequestParam(name = "content", defaultValue = "") String name,
                                                                    @RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {
        CompletableFuture<Long> total = datChoService.countDatCho(name);
        CompletableFuture<List<DatCho>> tks = datChoService.findDatChoWithPaginationAndSearch((long) page * size, size, name, orderBy);
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
