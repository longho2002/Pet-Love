package com.stc.petlove.repositories;

import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DatChoRepository extends MongoRepository<DatCho, String> {
    @Query(value = "{'id': ?0}")
    public Optional<DatCho> findById(String id);
}
