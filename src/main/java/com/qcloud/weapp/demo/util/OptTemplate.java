package com.qcloud.weapp.demo.util;

import com.qcloud.weapp.demo.db.DBConnection;
import com.qcloud.weapp.demo.servlet.wechatPay.PayResult;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zsh1995 on 2017/6/16.
 */
public class OptTemplate {
    private static final Logger L = Logger.getLogger(PayResult.class);

    public Object find(String sql, Object[] args,ObjectMapper mapper){
        Object resultObj = null;
        Connection conn = null;
        PreparedStatement ppst = null;
        try {
            conn = DBConnection.getConn();
            ppst= conn.prepareStatement(sql);
            for(int cnt = 0; cnt < args.length;cnt++){
                ppst.setObject(cnt+1,args[cnt]);
            }
            ResultSet rs = ppst.executeQuery();
            if(rs.next()){
                resultObj = mapper.mapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, ppst);
        }
        return resultObj;
    }

    public List<? extends Object> query(String sql,Object[] args,ObjectMapper mapper){
        List resultObj = new ArrayList();
        Connection conn = null;
        PreparedStatement ppst = null;
        try {
            conn = DBConnection.getConn();
            ppst= conn.prepareStatement(sql);
            for(int cnt = 0; cnt < args.length;cnt++){
                ppst.setObject(cnt+1,args[cnt]);
            }
            ResultSet rs = ppst.executeQuery();
            while (rs.next()){
                resultObj.add(mapper.mapping(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeAll(conn, ppst);
        }
        return resultObj;
    }

    public boolean update(String sql,Object[] args,boolean isGenerateKey){
        Connection conn = null;
        PreparedStatement ppst = null;
        boolean successFlag = false;
        try {
            conn = DBConnection.getConn();
            ppst = isGenerateKey? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                    : conn.prepareStatement(sql,Statement.NO_GENERATED_KEYS);
            for(int cnt = 0;cnt < args.length;cnt++){
                ppst.setObject(cnt+1,args[cnt]);
            }
            conn.setAutoCommit(false);
            int successNum = ppst.executeUpdate();

            if(isGenerateKey){
                ResultSet rs = ppst.getGeneratedKeys();
                if(rs.next()){
                    Long id = rs.getLong(1);
                    args[0] = id;
                }
            }
            conn.commit();

            if(successNum > 0 ){
                L.error("sql:"+successNum);
                successFlag = true;
            }
        } catch (SQLException e) {
            L.error("sqlerro:",e);
            e.printStackTrace();


        }finally {
            closeAll(conn,ppst);
        }
        return successFlag;
    }

    //关闭连接
    private void closeAll(Connection conn, PreparedStatement ppst) {
        try {
            if(conn != null)
                conn.close();
            if(ppst != null)
                ppst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
