package com.ntu.java.weather.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final Logger logger = Logger.getLogger(ConnectionUtil.class);
    private static GetFromProperties getFromProperties = new GetFromProperties();
    private static final String URL = "jdbc:mysql://localhost:3306/" + getSchemaDb() + "?serverTimezone=UTC";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = getUserDb();
    private static final String PASSWORD = getPasswordDb();
    private static Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connection successfully");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver was not loaded :", e);
            System.exit(1);
        } catch (SQLException e) {
            logger.error("Connection to DB was not established: ", e);
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private static String getUserDb() {
        return getFromProperties.getPropertiesValue("USER_DB");
    }

    private static String getPasswordDb() {
        return getFromProperties.getPropertiesValue("PASSWORD_DB");
    }

    private static String getSchemaDb() {
        return getFromProperties.getPropertiesValue("SCHEMA_DB");
    }
}
