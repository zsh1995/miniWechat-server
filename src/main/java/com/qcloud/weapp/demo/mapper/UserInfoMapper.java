package com.qcloud.weapp.demo.mapper;

import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.util.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/6/18.
 */
public class UserInfoMapper implements ObjectMapper {
    @Override
    public Object mapping(ResultSet set) {
        UserInfoDTO userInfoDTO = null;
        userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId((Integer) safeResultGet(set,"id"));
        userInfoDTO.setOpenId((String) safeResultGet(set,"openId"));
        userInfoDTO.setAvatarUrl((String) safeResultGet(set,"avatar_url"));
        userInfoDTO.setNickName((String) safeResultGet(set,"wechat_nick"));
        userInfoDTO.setStudent_name((String) safeResultGet(set,"student_name"));
        userInfoDTO.setSchool((String) safeResultGet(set,"school"));
        userInfoDTO.setMajor((String) safeResultGet(set,"major"));
        userInfoDTO.setPhoneNumber((String) safeResultGet(set,"phone_number"));
        userInfoDTO.setPost( (String) safeResultGet(set,"post"));
        userInfoDTO.setType( (String) safeResultGet(set,"type"));
        userInfoDTO.setCity( (String) safeResultGet(set,"city"));
        userInfoDTO.setGender( (String) safeResultGet(set,"gender"));
        userInfoDTO.setCompany( (String) safeResultGet(set,"company"));
        userInfoDTO.setWanted_company1((String) safeResultGet(set,"wanted_company1"));
        userInfoDTO.setWanted_company2((String) safeResultGet(set,"wanted_company2"));
        userInfoDTO.setWanted_company3((String) safeResultGet(set,"wanted_company3"));
        userInfoDTO.setWanted_company4((String) safeResultGet(set,"wanted_company4"));
        userInfoDTO.setWanted_company5((String) safeResultGet(set,"wanted_company5"));
        userInfoDTO.setInvitor((String)safeResultGet(set,"invitor"));
        userInfoDTO.setEmailAddr((String)safeResultGet(set,"emailAddr"));
        return userInfoDTO;
    }
    private Object safeResultGet(ResultSet set, String columnLabel){
        Object obj = null;
        try {
            obj = set.getObject(columnLabel);
        } catch (SQLException e) {
            obj = null;
        }
        return obj;
    }
}
