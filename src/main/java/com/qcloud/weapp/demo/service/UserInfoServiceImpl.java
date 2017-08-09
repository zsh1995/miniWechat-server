package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dao.UserDAO;
import com.qcloud.weapp.demo.dao.WechatPayDAO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
public class UserInfoServiceImpl implements UserInfoService {

    UserDAO userDao = new UserDAO();
    WechatPayDAO payDAO = new WechatPayDAO();
    @Override
    public UserInfoDTO getUserInfo(String openId) {
        return userDao.getUserInfo(openId);
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO) {
        return userDao.updateUserInfo(userInfoDTO);
    }

    @Override
    public Map getInvitor(Long id) {
        return userDao.selectInvitor(id);
    }

    @Override
    public boolean updateUserInvitor(Long id, String openId) {
        return userDao.updateInvitor(id,openId);
    }

    @Override
    public List getPurchExamRecord(String openId) {
        return (List<Map>) payDAO.getPurchExamRecord(openId);
    }

}
