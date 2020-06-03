package com.ntu.java.weather.dao;

import com.ntu.java.weather.model.Query;

import java.util.List;
import java.util.Optional;

public interface QueryDao {
    void addQuery(Query query);

    Optional<Query> getQueryByValue(String value);

    void deleteById(Long id);

    void updateQuery(Query query, Long id);

    Optional<List<Query>> getAll();
}
