package com.epamTranings.bankSystem.utils.dbConnectionUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnectionUtil {
    final static Logger logger = LogManager.getLogger(MySQLConnectionUtil.class);

    /**
     * Connection with MySQL db using default parameters
     *
     * @return
     */
    public static Connection getMySQLConnection() {
        Properties dbProperties = initProperties();

        if (dbProperties != null) {

            String hostName = dbProperties.getProperty("hostName");
            String dbName = dbProperties.getProperty("dbName");
            String userName = dbProperties.getProperty("userName");
            String password = dbProperties.getProperty("password");
            String serverTimeZone = dbProperties.getProperty("serverTimeZone");

            return getMySQLConnection(hostName, dbName, userName, password, serverTimeZone);
        }

        logger.error("error while properties were loading");
        return null;
    }

    /**
     * Connection with MySQL db using user parameters
     * @param hostName
     * @param dbName
     * @param userName
     * @param password
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password, String serverTimeZone) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("error with finding com.mysql.cj.jdbc.Driver");
            e.printStackTrace();
        }

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + serverTimeZone;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            logger.error("error with connection creation");
            e.printStackTrace();
        }

        logger.info("Connection created successfully");
        return connection;
    }

    /**
     * Getting parameters to initiate db connection
     *
     * @return
     */
    private static Properties initProperties() {
        Properties dbProperties = new Properties();
        try (InputStream in = MySQLConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            dbProperties.load(in);
            return dbProperties;
        } catch (IOException e) {
            logger.error("Properties didn't loaded");
            e.printStackTrace();
        }

        return null;
    }
}
