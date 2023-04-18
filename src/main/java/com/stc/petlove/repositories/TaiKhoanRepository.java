package com.stc.petlove.repositories;

import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanRepository extends MongoRepository<TaiKhoan, String> {
    @Query(value = "{'email': ?0}")
    Optional<TaiKhoan> getUser(String email);

    @Query(value = "{'id': ?0}")
    public Optional<TaiKhoan> findById(String id);

    @Aggregation(pipeline = {
            "{  $match: {name: { $regex: ?2, $options: 'i' }} }",
            "{ $skip: ?0 }",
            "{ $limit: ?1 }"
    })
    List<TaiKhoan> findTaiKhoanWithPaginationAndSearch(long skip, int limit, String name);

    @Aggregation(pipeline = {
            "{  $match: {name: { $regex: ?0, $options: 'i' }} }",
            "{ $group: { _id: null, count: { $sum: 1 } } }",
            "{ $project: { _id: 0 } }"
    })
    Optional<Long> countTaiKhoan(String name);
}
