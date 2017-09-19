package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.common.MapperTools;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.entity.Coupon;
import com.qcloud.weapp.demo.entity.UserCoupon;
import com.qcloud.weapp.demo.mapper.QuestionMapper;
import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public class CouponDAO {


    public boolean inserNewCouponRel(String openId, String coupon){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO coupon_user_rel (user_openId, couponId, create_time, remain_times) SELECT ?, id, NOW(), aval_times FROM coupon WHERE code_name = ?";
        Object[] args={openId,coupon};
        return optTemplate.update(sql,args,false);
    }

    public List<Coupon> queryCouponOfUser(String openId,String couponName){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT cp.code_name, cur.remain_times AS aval_times FROM coupon_user_rel cur LEFT JOIN coupon cp ON cp.id = cur.couponId WHERE cur.user_openId = ? AND cur.delete_flag = 0 AND cur.remain_times > 0 AND cp.code_name= ? ";
        Object[] args = {openId,couponName};
        return (List<Coupon>) optTemplate.query(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                return MapperTools.rsMapEntity(Coupon.class,set);
            }
        });
    }

    public boolean useCoupon(String openId,String couponName){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE coupon_user_rel cur LEFT JOIN coupon cp ON cp.id = cur.couponId SET remain_times = cur.remain_times - 1 WHERE cur.user_openId = ? AND cp.code_name = ?";
        Object[] args = {openId,couponName};
        return optTemplate.update(sql,args,false);
    }

    public boolean logUseCoupon(String openId,String couponName){
        OptTemplate optTemplate = new OptTemplate();
        String sql ="INSERT INTO coupon_record (`user_openId`,`coupon_id`,`create_time`) SELECT ?,id,NOW() FROM coupon WHERE code_name = ?";
        Object[] args = {openId,couponName};
        return optTemplate.update(sql,args,false);
    }

    public boolean analyseCouponUse(String openId,int questionId,int star){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO wechat_user_right ( openId,type,questionId,star,create_time) VALUES (?,'analyse',?,?,NOW())";
        Object[] args = {openId,questionId,star};
        return optTemplate.update(sql,args,false);
    }

    public boolean examCouponUse(String openId,int star){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO wechat_user_right( openId, type, star, remain_times, create_time) SELECT ？,'exam',？,dict_value,NOW() FROM dict_table WHERE dict_name = 'TIMES_PER_PURCH'";
        Object[] args = {openId,star};
        return optTemplate.update(sql,args,false);
    }

    public List<UserCoupon> queryAllCoupons(String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT couponId,remain_times FROM coupon_user_rel WHERE user_openId = ? AND remain_times > 0";
        Object[] args ={openId};
        return (List<UserCoupon>) optTemplate.query(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                return MapperTools.rsMapEntity(UserCoupon.class,set);
            }
        });
    }



}
