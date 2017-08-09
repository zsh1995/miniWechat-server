package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dto.UserInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface UserInfoService {

    UserInfoDTO getUserInfo(String openId);

    boolean updateUserInfo(UserInfoDTO userInfoDTO);

    Map getInvitor(Long id);

    boolean updateUserInvitor(Long id,String openId);

    List getPurchExamRecord(String openId);

}
