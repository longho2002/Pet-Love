package com.stc.petlove.repositories;

import com.stc.petlove.entities.DatCho;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DatChoRepository extends MongoRepository<DatCho, String> {
    @Query(value = "{'id': ?0}")
    public Optional<DatCho> findById(String id);

    @Aggregation(pipeline = {
            "{  $match: {email: { $regex: ?2, $options: 'i' }} }",
            "{ $skip: ?0 }",
            "{ $limit: ?1 }"
    })
    List<DatCho> findDatChoWithPaginationAndSearch(long skip, int limit, String name);

    @Aggregation(pipeline = {
            "{  $match: {email: { $regex: ?0, $options: 'i' }} }",
            "{ $group: { _id: null, count: { $sum: 1 } } }",
            "{ $project: { _id: 0 } }"
    })
    Optional<Long> countDatCho(String name);
}
