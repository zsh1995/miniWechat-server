package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dao.UserDAO;
import com.qcloud.weapp.demo.dao.WechatPayDAO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.entity.EnterpriseInfo;
import com.qcloud.weapp.demo.service.company.CompanyService;
import com.qcloud.weapp.demo.service.company.CompanyServiceImpl;
import com.sun.xml.internal.xsom.impl.ComponentImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
public class UserInfoServiceImpl implements UserInfoService {

    UserDAO userDao = new UserDAO();
    WechatPayDAO payDAO = new WechatPayDAO();
    CompanyService companyService = new CompanyServiceImpl();
    @Override
    public UserInfoDTO getUserInfo(String openId) {
        return userDao.getUserInfo(openId);
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO){
        long id = userDao.getUserInfo(userInfoDTO.getOpenId()).getId();
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWanted_company1());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWanted_company2());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWanted_company3());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWanted_company4());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWanted_company5());companyService.addNewCompany(enterpriseInfo,id);

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

    @Override
    public List<Map> getAllInvite(String openId) {
        return userDao.queryAllInvite(openId);
    }
    @Override
    public Map getMyInvitor(String openId){
        return userDao.getMyInvitor(openId);
    }

}
