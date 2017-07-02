package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.mapper.UserInfoMapper;
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

    public UserInfoDTO getUserInfo(String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT student_name,school,major,phone_number FROM wechat_userinfo WHERE openid = ?";
        String[] args = {openId};
        return (UserInfoDTO) optTemplate.find(sql,args,new UserInfoMapper());
    }

    public boolean updateUserInfo(UserInfoDTO userInfo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE wechat_userinfo\n" +
                "SET student_name =?,school =?,major = ?,phone_number = ?\n" +
                "WHERE\n" +
                "	openId =?";
        String[] args = {userInfo.getStudent_name(),userInfo.getSchool(),userInfo.getMajor(),userInfo.getPhoneNumber(),userInfo.getOpenId()};
        return optTemplate.update(sql,args,false);
    }
}
