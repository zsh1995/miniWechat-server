package com.qcloud.weapp.demo.mapper;

import com.qcloud.weapp.demo.common.MapperTools;
import com.qcloud.weapp.demo.dto.weixinPay.WechatResult;
import com.qcloud.weapp.demo.util.ObjectMapper;

import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/7/5.
 */
public class WechatResultMapper implements ObjectMapper {
    @Override
    public Object mapping(ResultSet set) {
        WechatResult wechatResult = (WechatResult)MapperTools.rsMapEntity(WechatResult.class,set);
        return null;
    }
}
