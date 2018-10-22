package com.gino.lock;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author gino
 * Created on 2018/10/22
 */
@Slf4j
public class MySQLConfig {
    private static final String CONFIG = "config.properties";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/demo";
    private static String username = "root";
    private static String password = "root";

    protected static Connection connection;

    static {
        try {
            Class.forName(DRIVER);
            log.info("MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log.error("Not found MySQL JDBC Driver?");
            e.printStackTrace();
        }

        ClassLoader classLoader = MySQLConfig.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(CONFIG);
        if (inputStream != null) {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            url = properties.getProperty("url", url);
            username = properties.getProperty("username", username);
            password = properties.getProperty("password", password);
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
