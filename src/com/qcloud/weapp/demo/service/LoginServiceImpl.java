package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dao.UserDAO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;

/**
 * Created by Administrator on 2017/6/18.
 */
public class LoginServiceImpl implements LoginService {
    @Override
    public boolean doLogin(UserInfo userInfo) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setOpenId(userInfo.getOpenId());
        dto.setNickName(userInfo.getNickName());
        dto.setAvatarUrl(userInfo.getAvatarUrl());
        return new UserDAO().insertNewLoginUser(dto);
    }
}
