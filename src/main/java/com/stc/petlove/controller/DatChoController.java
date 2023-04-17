package com.stc.petlove.controller;

import com.stc.petlove.annotations.ApiPrefixController;
import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.services.DatCho.IDatChoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController("/datcho")
public class DatChoController {
    private final IDatChoService datChoService;

    public DatChoController(IDatChoService datChoService) {
        this.datChoService = datChoService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<DatCho> create(DatChoDto input) {
        return datChoService.create(input);
    }

    @GetMapping(value = "/{id}")
    public CompletableFuture<DatCho> getOne(@PathVariable(value = "id") String id) {
        return datChoService.getOne(id);

    }

    @GetMapping()
    public CompletableFuture<List<DatCho>> getAll() {
        return datChoService.getAll();
    }

    @PatchMapping("/{id}")
    public CompletableFuture<DatCho> update(@PathVariable(value = "id") String id, @RequestBody DatCho data) {
        return datChoService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> remove(@PathVariable(value = "id") String id) {
        return datChoService.remove(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/setTrangThai/{id}")
    public CompletableFuture<DatCho> setTrangThai(@PathVariable(value = "id") String id, @RequestParam(name = "trangthai") boolean trangThai) {
        return datChoService.setTrangThai(id, trangThai);
    }
}
