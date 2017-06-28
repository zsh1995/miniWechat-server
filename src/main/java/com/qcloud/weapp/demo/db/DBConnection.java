package com.qcloud.weapp.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zsh1995 on 2017/6/16.
 */
public class DBConnection {

    private static String db_url = "jdbc:mysql://10.66.217.225:3306/tinyProgram_BE?useUnicode=true&characterEncoding=utf-8&useSSL=true";

    private static String user_name="wechat_all";

    private static String user_pwd="zsh19950314#";

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
            if (conn  == null){
                throw new Exception("conn is null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
