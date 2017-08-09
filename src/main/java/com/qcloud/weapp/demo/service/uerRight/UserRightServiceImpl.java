package com.qcloud.weapp.demo.service.uerRight;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dao.UserDAO;
import com.qcloud.weapp.demo.entity.WechatUserRight;

/**
 * Created by Administrator on 2017/7/9.
 */
public class UserRightServiceImpl implements UserRightService {

    UserDAO userDAO = new UserDAO();

    @Override
    public int checkUserRight(String openId, int type, int star, int questionId) throws Exception {

        WechatUserRight userInformation = new WechatUserRight();
        userInformation.setOpenId(openId);
        userInformation.setQuestionId(questionId);
        userInformation.setStar(star);

        if(ApiConst.PURCH_TYPE_EXAM == type){

            userInformation.setType(ApiConst.RIGHT_TYPE_EXAM);
            //查询用户权限信息
            Integer remainTimes = userDAO.selecteUserRightRemainTimes(userInformation);
            if(remainTimes == null){
                remainTimes =0;
            }
            return remainTimes;
        } else if(ApiConst.PURCH_TYPE_ANALYSE == type){
            //
            userInformation.setType(ApiConst.RIGHT_TYPE_ANALYSE);
            Long returnId = userDAO.selecteUserRightAnalyse(userInformation);
            int resultId = 0;
            if(returnId != null){
                resultId = returnId.intValue();
            }
            return resultId;
        }
        throw new Exception("参数错误");
    }

    @Override
    public boolean updateUserRight(String openId, int star, int remainTimes) {

        WechatUserRight userRight = new WechatUserRight();

        userRight.setOpenId(openId);
        userRight.setStar(star);
        userRight.setRemainTimes(remainTimes);
        userRight.setType(ApiConst.RIGHT_TYPE_EXAM);

        return userDAO.updateUserRightRemainTimes(userRight);
    }

    @Override
    public boolean insertNewExamRight(String openId, int star) throws Exception {
        WechatUserRight userRight = new WechatUserRight();

        userRight.setType(ApiConst.RIGHT_TYPE_EXAM);
        userRight.setOpenId(openId);
        userRight.setStar(star);
        return userDAO.insertNewUserRight(userRight);
    }

    @Override
    public boolean insertNewAnalyseRight(String openId, int questionId) throws Exception {
        WechatUserRight userRight = new WechatUserRight();

        userRight.setType(ApiConst.RIGHT_TYPE_ANALYSE);
        userRight.setOpenId(openId);
        userRight.setQuestionId(questionId);
        return userDAO.insertNewUserRight(userRight);
    }

    @Override
    public boolean inserUserRight(String openId, String tradeNo) {
        return userDAO.insertUserRightByPurchedInfo(openId,tradeNo);
    }

    @Override
    public boolean updateUserExamStatus(String openId, String type, int star, int passExam) {
        WechatUserRight userRight = new WechatUserRight();
        userRight.setOpenId(openId);
        userRight.setType(type);
        userRight.setPassExam(passExam);
        userRight.setStar(star);
        return userDAO.updateUserRightExamStatus(userRight);
    }

    @Override
    public int getExamStatus(String openId, int star) throws Exception {
        Integer passExam = userDAO.getExamStatus(openId,star);
        if(passExam == null){ throw new Exception("当前用户未购买");}
        return passExam;
    }


}
