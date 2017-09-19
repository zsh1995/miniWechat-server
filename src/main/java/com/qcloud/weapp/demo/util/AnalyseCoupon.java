package com.qcloud.weapp.demo.util;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dao.CouponDAO;
import com.qcloud.weapp.demo.service.coupon.CouponService;
import com.qcloud.weapp.demo.service.coupon.CouponServiceImpl;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
public class AnalyseCoupon implements CouponUse {

    private CouponDAO couponDAO = new CouponDAO();

    private CouponService couponService = new CouponServiceImpl();

    @Override
    public boolean useCoupon(String openId, Map data) throws Exception {

        if(!couponService.useCoupon(openId, ApiConst.ANALYSE_FREE_THREE_TIME)){
            throw new Exception("使用券出错--修改次数出错");
        }
        if(!couponDAO.analyseCouponUse(openId,(int)data.get("questionId"),(int)data.get("star"))){
            throw new Exception("使用券出错--插入权限信息错误");
        }
        return true;
    }
}
