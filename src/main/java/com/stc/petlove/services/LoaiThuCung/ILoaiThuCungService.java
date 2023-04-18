package com.stc.petlove.services.LoaiThuCung;

import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.services.IService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ILoaiThuCungService extends IService<LoaiThuCung, LoaiThuCungDto> {
    public CompletableFuture<List<LoaiThuCung>> findLoaiThuCungWithPaginationAndSearch(long skip, int limit, String name, String orderBy);

    public CompletableFuture<Long> countLoaiThuCung (String name);
}
