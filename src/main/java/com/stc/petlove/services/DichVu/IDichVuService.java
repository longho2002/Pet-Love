package com.stc.petlove.services.DichVu;

import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.embedded.GiaDichVu;
import com.stc.petlove.services.IService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IDichVuService extends IService<DichVu, DichVuDto> {
    public CompletableFuture<List<DichVu>> findDichVuWithPaginationAndSearch(long skip, int limit, String name);

    public CompletableFuture<Long> countDichVu (String name);

    public CompletableFuture<DichVu> addGiaDichVu (String id, GiaDichVu input);
}
