package com.stc.petlove.repositories;

import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.LoaiThuCung;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoaiThuCungRepository extends MongoRepository<LoaiThuCung, String> {
    @Query(value = "{'id': ?0}")
    public Optional<LoaiThuCung> findById(String id);
    @Query(value = "{'maLoaiThuCung': ?0}")
    public Optional<LoaiThuCung> findByMaLoaiThuCung(String maLoaiThuCung);

    @Aggregation(pipeline = {
            "{  $match: {tenLoaiThuCung: { $regex: ?2, $options: 'i' }} }",
            "{ $skip: ?0 }",
            "{ $limit: ?1 }"
    })
    List<LoaiThuCung> findLoaiThuCungWithPaginationAndSearch(long skip, int limit, String name);

    @Aggregation(pipeline = {
            "{  $match: {tenLoaiThuCung: { $regex: ?0, $options: 'i' }} }",
            "{ $group: { _id: null, count: { $sum: 1 } } }",
            "{ $project: { _id: 0 } }"
    })
    Optional<Long> countLoaiThuCung(String name);
}
