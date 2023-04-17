package com.stc.petlove.repositories;

import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DichVuRepository extends MongoRepository<DichVu, String> {

    @Query(value = "{'id': ?0}")
    public Optional<DichVu> findById(String id);

    @Query(value = "{'maDichVu': ?0}")
    public Optional<DichVu> findByMaDichVu(String id);
}
