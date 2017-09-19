package com.qcloud.weapp.demo.service.coupon;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dao.CouponDAO;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public class CouponServiceImpl implements CouponService {
    private CouponDAO couponDAO= new CouponDAO();
    @Override
    public boolean bindCoupon(String openId, String couponName) {
        //数据库操作
        return couponDAO.inserNewCouponRel(openId,couponName);
    }

    @Override
    public boolean bindAnalyseCoupon(String openId) {
        return bindCoupon(openId, ApiConst.ANALYSE_FREE_THREE_TIME);
    }

    @Override
    public boolean bindExamCoupon(String openId) {
        return bindCoupon(openId, ApiConst.EXAM_FREE_THREE_TIME);
    }


    @Override
    public List userCoupons(String openId,String couponName) {
        return couponDAO.queryCouponOfUser(openId,couponName);
    }

    @Override
    public List userAllCoupons(String openId) {
        return couponDAO.queryAllCoupons(openId);
    }

    @Override
    public boolean useCoupon(String openId, String couponName) throws Exception {
        //获取可用券
        if( userCoupons(openId, couponName).isEmpty() ){
            throw new Exception("无可用券");
        }
        //使用券
        if(!couponDAO.useCoupon(openId,couponName)){
            throw new Exception("使用券出错");
        };
        //记录
        if(!couponDAO.logUseCoupon(openId,couponName)){
            throw new Exception("记录出错");
        };
        return true;
    }


}
