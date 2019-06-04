package com.epamTranings.bankSystem.dbConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    final static Logger logger = LogManager.getLogger(ConnectionUtils.class);

    /**
     * MySQL connection returning
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        return MySQLConnectionUtil.getMySQLConnection();
    }

    /**
     * Closing connection quietly
     * @param connection
     */
    public static void closeQuietly(Connection connection){
        try {
            connection.close();
            logger.info("Connection closed quietly");
        } catch (SQLException e) {
            logger.error("Connection hasn't closed");
            e.printStackTrace();
        }
    }

    /**
     * Rolling back quietly
     * @param connection
     */
    public static void rollbackQuietly(Connection connection){
        try {
            connection.rollback();
            logger.info("Connection rolled back");
        } catch (SQLException e) {
            logger.error("Connection hasn't rolled back");
            e.printStackTrace();
        }
    }
}
