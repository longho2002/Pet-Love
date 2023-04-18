package com.stc.petlove.services.DatCho;

import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.services.IService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IDatChoService extends IService<DatCho, DatChoDto> {
    public CompletableFuture<List<DatCho>> findDatChoWithPaginationAndSearch(long skip, int limit, String name, String orderBy);

    public CompletableFuture<Long> countDatCho (String name);
    public CompletableFuture<DatCho> updateTrangThai (String id ,int status);
}
