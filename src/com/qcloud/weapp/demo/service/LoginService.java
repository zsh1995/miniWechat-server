package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.UserInfoDTO;

/**
 * Created by Administrator on 2017/6/18.
 */
public interface LoginService {

    boolean doLogin(UserInfo userInfo);

}