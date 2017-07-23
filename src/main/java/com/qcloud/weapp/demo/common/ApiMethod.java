package com.qcloud.weapp.demo.common;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/7/14.
 */
public class ApiMethod {

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
        result.put("message", e);
    }

}
