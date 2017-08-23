package com.qcloud.weapp.demo.common;

import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/7/14.
 */
public class ApiMethod {

    public static Integer getConstant(String constantName){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT dict_value FROM dict_table WHERE dict_name = ?";
        Object[] args={constantName};
        return (Integer) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                try{
                    return set.getInt("dict_value");
                } catch (SQLException e) {
                    return -1;
                }
            }
        });
    }

    public static void formateResultWithDate(JSONObject result,Object data){
        result.put("data",data);
        result.put("code", 0);
        result.put("message", "OK");
    }

    public static void formateResultWithNothing(JSONObject result,boolean success){
        if(success){
            result.put("code", 0);
            result.put("message", "OK");
        } else {
            result.put("code", 1);
            result.put("message", "erro");
        }
    }

    public static void formateResultWithExcp(JSONObject result,Exception e){
        result.put("code", 1);
        result.put("message", e.getMessage());
    }

}
