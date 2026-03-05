package com.ccms.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DBConnection {

    private static HikariDataSource dataSource;

    static {
        try {

            System.out.println("Initializing HikariCP Connection Pool...");
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:mysql://localhost:3306/ccms");
            config.setUsername("root");
            config.setPassword("Stark@2803");

            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Connection pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(20000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);

            System.out.println("HikariCP Pool Initialized Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}