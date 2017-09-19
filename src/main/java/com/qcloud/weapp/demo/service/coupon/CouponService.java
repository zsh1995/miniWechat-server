package com.qcloud.weapp.demo.service.coupon;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface CouponService {


    //绑定一张优惠券
    boolean bindCoupon(String openId,String CouponName);

    boolean bindAnalyseCoupon(String openId);

    boolean bindExamCoupon(String openId);

    //获取用户当前可用优惠券
    List userCoupons(String openId,String couponName);

    //
    List userAllCoupons(String openId);


    boolean useCoupon(String openId, String couponName) throws Exception;
}
