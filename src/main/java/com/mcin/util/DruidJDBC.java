package com.mcin.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by Mcin on 2017/5/15.
 */
public class DruidJDBC {
    private static final Logger log = Logger.getLogger(DruidJDBC.class);

    static final String HOST = "localhoqewqst";
    static final int PORT = 3306;
    static final String MYSQL_DB = "cmq";
    static final String MYSQL_URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+MYSQL_DB;
    static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    static final String MYSQL_USER_NAME = "root";
    static final String MYSQL_PASS_WORD = "233";

    /**
     * 连接数据库
     * @throws SQLException
     */
    public static void conn () {
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(MYSQL_DRIVER);
            dataSource.setUsername(MYSQL_USER_NAME);
            dataSource.setPassword(MYSQL_PASS_WORD);
            dataSource.setUrl(MYSQL_URL);
            dataSource.setInitialSize(5);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(10); // 启用监控统计功能
            dataSource.setFilters("stat");// for mysql  dataSource.setPoolPreparedStatements(false);
            log.info("数据库连接成功");
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("数据库连接发生错误");
        } finally {

        }
    }

    public static void main (String[] args){
        conn();
    }
}
