package com.qcloud.weapp.demo.entity;

/**
 * Created by Administrator on 2017/8/25.
 */
public class UserCoupon {

    private int couponId;

    private int remainTimes;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getRemainTimes() {
        return remainTimes;
    }

    public void setRemainTimes(int remainTimes) {
        this.remainTimes = remainTimes;
    }
}
