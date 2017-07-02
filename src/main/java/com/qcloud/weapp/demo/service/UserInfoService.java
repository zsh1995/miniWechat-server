package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dto.UserInfoDTO;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface UserInfoService {

    UserInfoDTO getUserInfo(String openId);

    boolean updateUserInfo(UserInfoDTO userInfoDTO);
}
