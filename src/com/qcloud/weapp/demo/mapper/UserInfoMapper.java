package com.qcloud.weapp.demo.mapper;

import com.qcloud.weapp.demo.dto.QuestionDTO;
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
        try {
            userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(set.getInt("id"));
            userInfoDTO.setOpenId(set.getString("openId"));
            userInfoDTO.setAvatarUrl(set.getString("avatar_url"));
            userInfoDTO.setNickName(set.getString("wechat_nick"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfoDTO;
    }
}
