package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.util.OptTemplate;

/**
 * Created by Administrator on 2017/6/17.
 */
public class UserDAO {
    public boolean insertNewLoginUser(UserInfoDTO userinfo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO wechat_userinfo(wechat_nick,openId,avatar_url) VALUE(?,?,?) ON DUPLICATE KEY UPDATE wechat_nick= ?,openId=?,avatar_url=?";
        String[] args = {userinfo.getNickName(),userinfo.getOpenId(),userinfo.getAvatarUrl(),userinfo.getNickName(),userinfo.getOpenId(),userinfo.getAvatarUrl()};
        return optTemplate.update(sql,args,false);
    }
}
