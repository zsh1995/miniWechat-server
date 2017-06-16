package com.qcloud.weapp.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zsh1995 on 2017/6/16.
 */
public class DBConnection {

    private static String db_url = "jdbc:mysql://47.93.196.237:3306/wechat-BE?useUnicode=true&characterEncoding=utf-8";

    private static String user_name="mrzsh";

    private static String user_pwd="123456";

    private static Connection conn= null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        try {
            conn = DriverManager.getConnection(db_url,user_name,user_pwd);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn(){
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
