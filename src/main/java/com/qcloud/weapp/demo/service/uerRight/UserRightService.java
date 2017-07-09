package com.qcloud.weapp.demo.service.uerRight;

import com.qcloud.weapp.demo.entity.WechatUserRight;

/**
 * Created by Administrator on 2017/7/9.
 */
public interface UserRightService {

    //查询用户权限信息
    int checkUserRight(String openId,int type, int star, int questionId) throws Exception;

    //更新用户权限信息
    boolean updateUserRight(String openId,int star,int remainTimes);

    //插入新的考试权限
    boolean insertNewExamRight(String openId,int star) throws Exception;

    //插入新的解析权限
    boolean insertNewAnalyseRight(String openId,int questionId) throws Exception;

}
