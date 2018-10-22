package com.gino.lock;

import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author gino
 * Created on 2018/10/22
 */
@Slf4j
public class MySQLLock {
    private final static String insertSQL = "insert into method_lock(method_name) values(?)";
    private final static String clearSQL = "delete from method_lock where method_name=? and current_timestamp()-update_time>?";
    private final static String deleteSQL = "delete from method_lock where method_name=?";

    private static boolean getLock(String name) {
        try {

            PreparedStatement preparedDelete = MySQLConfig.connection.prepareStatement(clearSQL);
            preparedDelete.setString(1, name);
            // 3 seconds
            preparedDelete.setInt(2, 3);
            preparedDelete.executeUpdate();

            PreparedStatement preparedInsert = MySQLConfig.connection.prepareStatement(insertSQL);
            preparedInsert.setString(1, name);

            MySQLConfig.connection.commit();

            log.info("get lock....");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLockTimes(String name, int times, int interval) {
        int count = 0;
        while (count < times) {
            boolean lock = getLock(name);
            if (!lock) {
                count++;
                try {
                    log.info("not get lock, sleep and retry....");
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean releaseLock(String name) {
        try {
            PreparedStatement preparedDelete = MySQLConfig.connection.prepareStatement(deleteSQL);
            preparedDelete.setString(1, name);
            preparedDelete.executeUpdate();
            MySQLConfig.connection.commit();
            log.info("release lock....");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
