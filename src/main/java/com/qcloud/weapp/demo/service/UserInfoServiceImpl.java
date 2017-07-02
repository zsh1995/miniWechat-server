package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dao.UserDAO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;

/**
 * Created by Administrator on 2017/7/2.
 */
public class UserInfoServiceImpl implements UserInfoService {

    UserDAO userDao = new UserDAO();
    @Override
    public UserInfoDTO getUserInfo(String openId) {
        return userDao.getUserInfo(openId);
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO) {
        return userDao.updateUserInfo(userInfoDTO);
    }
}
