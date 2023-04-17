package com.stc.petlove.repositories;

import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface TaiKhoanRepository extends MongoRepository<TaiKhoan, String> {
    @Query(value = "{'email': ?0}")
    Optional<TaiKhoan> getUser(String email);

    @Query(value = "{'id': ?0}")
    public Optional<TaiKhoan> findById(String id);


}
