package com.stc.petlove.repositories;

import com.stc.petlove.entities.LoaiThuCung;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface LoaiThuCungRepository extends MongoRepository<LoaiThuCung, String> {
    @Query(value = "{'id': ?0}")
    public Optional<LoaiThuCung> findById(String id);

}
