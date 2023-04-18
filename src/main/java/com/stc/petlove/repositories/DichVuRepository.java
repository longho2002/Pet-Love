package com.stc.petlove.repositories;

import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.DichVu;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DichVuRepository extends MongoRepository<DichVu, String> {

    @Query(value = "{'id': ?0}")
    public Optional<DichVu> findById(String id);

    @Query(value = "{'maDichVu': ?0}")
    public Optional<DichVu> findByMaDichVu(String id);


    @Aggregation(pipeline = {
            "{  $match: {tenDichVu: { $regex: ?2, $options: 'i' }} }",
            "{ $skip: ?0 }",
            "{ $limit: ?1 }"
    })
    List<DichVu> findDichVuWithPaginationAndSearch(long skip, int limit, String name);

    @Aggregation(pipeline = {
            "{  $match: {tenDichVu: { $regex: ?0, $options: 'i' }} }",
            "{ $group: { _id: null, count: { $sum: 1 } } }",
            "{ $project: { _id: 0 } }"
    })
    Optional<Long> countDichVu(String name);
}
