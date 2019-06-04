package com.epamTranings.bankSystem.dbConnection;

import com.epamTranings.bankSystem.servlet.MainServlet;
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
    private static Properties dbProperties;

    /**
     * Connection with MySQL db using default parameters
     *
     * @return
     */
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        if (initProperties()) {

            String hostName = dbProperties.getProperty("hostName");
            String dbName = dbProperties.getProperty("dbName");
            String userName = dbProperties.getProperty("userName");
            String password = dbProperties.getProperty("password");

            return getMySQLConnection(hostName, dbName, userName, password);
        }

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
    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection connection = DriverManager.getConnection(connectionURL, userName, password);

        return connection;
    }

    /**
     * Getting parameters to initiate db connection
     *
     * @return
     */
    private static boolean initProperties() {
        try (InputStream in = MySQLConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            dbProperties.load(in);
        } catch (IOException e) {
            logger.error("some problems with properties");
            e.printStackTrace();
        }

        return true;
    }
}
