package com.ntu.java.weather.dao.daoImpl;

import com.ntu.java.weather.dao.QueryDao;
import com.ntu.java.weather.model.Query;
import com.ntu.java.weather.util.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryDaoImpl implements QueryDao {
    private static final Connection connection = ConnectionUtil.getConnection();
    private static final Logger logger = Logger.getLogger(QueryDaoImpl.class);

    @Override
    public void addQuery(Query query) {
        String ADD_QUERY = "INSERT INTO query(name) VALUES (?);";
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_QUERY);
            statement.setString(1, query.getName());
            statement.executeUpdate();
            logger.info("insert query is done");
            logger.debug(ADD_QUERY);
        } catch (SQLException e) {
            logger.error("check your sql query", e);
        }
    }

    @Override
    public Optional<Query> getQueryByValue(String value) {
        String GET_CITY_BY_VALUE = "SELECT query_id, name" +
                " FROM query WHERE name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CITY_BY_VALUE);
            statement.setString(1, value);
            ResultSet getCityByValueResultSet = statement.executeQuery();
            while (getCityByValueResultSet.next()) {
                Long cityId = getCityByValueResultSet.getLong("query_id");
                String valueFromDb = getCityByValueResultSet.getString("name");
                Query query = new Query(cityId, valueFromDb);
                return Optional.of(query);
            }
            logger.info("get query by value is done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_QUERY_BY_ID =
                "DELETE FROM query WHERE query_id=? ";
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY_BY_ID);
            statement.setLong(1, id);
            statement.execute();
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateQuery(Query query, Long id) {
        final String UPDATE_QUERY =
                "UPDATE query SET name=? WHERE query_id=? ";
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, query.getName());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<Query>> getAll() {
        String SELECT_ALL = " SELECT * FROM query ";
        List<Query> addToList = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Query query = new Query();
                query.setId(rs.getLong("query_id"));
                query.setName(rs.getString("name"));
                addToList.add(query);
            }
            return Optional.of(addToList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
