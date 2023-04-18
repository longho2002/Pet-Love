package com.stc.petlove.services.TaiKhoan;

import com.stc.petlove.dtos.TaiKhoanDto;
import com.stc.petlove.dtos.UpdateProfileDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.IService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITaiKhoanService extends IService<TaiKhoan, TaiKhoanDto> {
    public CompletableFuture<TaiKhoan> getProfile();

    public CompletableFuture<TaiKhoan> updateProfile(UpdateProfileDto taiKhoan);

    public CompletableFuture<List<TaiKhoan>> findTaiKhoanWithPaginationAndSearch(long skip, int limit, String name, String orderBy);

    public CompletableFuture<Long> countTaiKhoan(String name);
}
