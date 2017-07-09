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
    public int checkUserRight(String openId,int type, int star, int questionId) throws Exception {

        WechatUserRight userInformation = new WechatUserRight();
        userInformation.setOpenId(openId);
        userInformation.setQuestionId(questionId);
        userInformation.setStar(star);;

        if(ApiConst.PURCH_TYPE_EXAM == type){

            //查询用户权限信息
            Integer remainTimes = userDAO.selecteUserRightRemainTimes(userInformation);
            if(remainTimes == null){
                remainTimes =0;
            }
            return remainTimes;
        } else if(ApiConst.PURCH_TYPE_ANALYSE == type){
            //
            Integer returnId = userDAO.selecteUserRightAnalyse(userInformation);
            if(returnId == null){
                returnId = 0;
            }
            return returnId;
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


}
