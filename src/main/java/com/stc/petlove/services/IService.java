package com.stc.petlove.services;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IService<T, D> {
    CompletableFuture<T> create(D input);
    CompletableFuture<T> getOne(String id);
    CompletableFuture<List<T>> getAll();
    CompletableFuture<T> update(String id, T data);
    CompletableFuture<Void> remove(String id);
    public CompletableFuture<T> setTrangThai(String id, boolean trangThai);
}
