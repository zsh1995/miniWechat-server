package main.java.com.qcloud.weapp.demo.util;

import java.sql.ResultSet;

/**
 * Created by zsh1995 on 2017/6/16.
 */
public interface ObjectMapper {

    public Object mapping(ResultSet set);
}
