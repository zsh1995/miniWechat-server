package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.entity.WechatUserRight;
import com.qcloud.weapp.demo.mapper.UserInfoMapper;
import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/6/17.
 */
public class UserDAO {
    public boolean insertNewLoginUser(UserInfoDTO userinfo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO wechat_userinfo(wechat_nick,openId,avatar_url) VALUE(?,?,?) ON DUPLICATE KEY UPDATE wechat_nick= ?,openId=?,avatar_url=?";
        String[] args = {userinfo.getNickName(),userinfo.getOpenId(),userinfo.getAvatarUrl(),userinfo.getNickName(),userinfo.getOpenId(),userinfo.getAvatarUrl()};
        return optTemplate.update(sql,args,false);
    }

    public UserInfoDTO getUserInfo(String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT student_name,school,major,phone_number,post,type,city,gender,company,wanted_company FROM wechat_userinfo WHERE openid = ?";
        String[] args = {openId};
        return (UserInfoDTO) optTemplate.find(sql,args,new UserInfoMapper());
    }

    public boolean updateUserInfo(UserInfoDTO userInfo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE wechat_userinfo\n" +
                "SET student_name =?,school =?,major = ?,phone_number = ?,post= ?,type= ?,city= ?,gender= ?,company= ?,wanted_company= ?\n" +
                "WHERE\n" +
                "	openId =?";
        String[] args = {userInfo.getStudent_name(),userInfo.getSchool(),userInfo.getMajor(),userInfo.getPhoneNumber(),
                userInfo.getPost(),userInfo.getType(),userInfo.getCity(),userInfo.getGender(),userInfo.getCompany(),userInfo.getWanted_company(),
                userInfo.getOpenId()};
        return optTemplate.update(sql,args,false);
    }

    public boolean insertNewUserRight(WechatUserRight userRight) throws Exception {
        OptTemplate optTemplate = new OptTemplate();
        String sql = "";
        if(ApiConst.RIGHT_TYPE_EXAM.equals(userRight.getType())){
            sql = "INSERT INTO wechat_user_right (\n" +
                    "\topenId,\n" +
                    "\ttype,\n" +
                    "\tstar,\n" +
                    "\tremain_times,\n" +
                    "\tcreate_time\n" +
                    ") SELECT\n" +
                    "\t?, ?, ?, dt.dict_value,\n" +
                    "\tNOW()\n" +
                    "FROM\n" +
                    "\tdict_table dt\n" +
                    "WHERE\n" +
                    "\tdt.dict_name = 'TIMES_PER_PURCH'";
            Object[] args = {userRight.getOpenId(),userRight.getType(),userRight.getStar()};
            return optTemplate.update(sql,args,false);
        } else if(ApiConst.RIGHT_TYPE_ANALYSE.equals(userRight.getType())){
            sql = "INSERT INTO wechat_user_right(openId,type,questionId,create_time) VALUE(?,?,?,NOW())";
            Object[] args = {userRight.getOpenId(),userRight.getType(),userRight.getQuestionId()};
            return optTemplate.update(sql,args,false);
        }
        throw new Exception("参数错误");
    }

    public boolean insertUserRightByPurchedInfo(String openId,String tradeNo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO wechat_user_right (openId, type, star, questionId, remain_times , create_time) (SELECT openId, per.type, per.purch_star, per.purch_question_id, dt.dict_value , NOW() FROM purch_exam_record per LEFT JOIN dict_table dt ON dt.dict_name = 'TIMES_PER_PURCH' WHERE per.trade_no = ? ) ON DUPLICATE KEY UPDATE remain_times = dt.dict_value,passExam = 0";
        Object[] args = {tradeNo};
        return optTemplate.update(sql,args,false);
    }


    public boolean updateUserRightRemainTimes(WechatUserRight userRight){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE wechat_user_right\n" +
                "SET remain_times =?\n" +
                "WHERE\n" +
                "	openId =? AND type = ? AND star = ?";
        Object[] args = {userRight.getRemainTimes(),userRight.getOpenId(),userRight.getType(),userRight.getStar()};
        return optTemplate.update(sql,args,false);
    }

    public boolean updateUserRightExamStatus(WechatUserRight userRight){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE wechat_user_right\n" +
                "SET passExam =passExam+1\n" +
                "WHERE\n" +
                "	openId =? AND type = ? AND star = ?";
        Object[] args = {userRight.getOpenId(),userRight.getType(),userRight.getStar()};
        return optTemplate.update(sql,args,false);
    }

    public Integer getExamStatus(String openId,int star){
        OptTemplate optTemplate = new OptTemplate();
        String sql ="SELECT passExam FROM wechat_user_right WHERE openId = ? AND star = ? AND type = 'exam' ";
        Object[] args ={openId,star};
        return (Integer) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                Integer intNum ;
                try {
                    intNum = set.getInt("passExam");
                    return intNum;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


    public Integer selecteUserRightRemainTimes(WechatUserRight userRight){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT remain_times FROM wechat_user_right WHERE openId = ? AND type = ? AND star = ?";
        Object[] args = {userRight.getOpenId(),userRight.getType(),userRight.getStar()};
        return (Integer) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                Integer intNum ;
                try {
                    intNum = set.getInt("remain_times");
                    return intNum;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public Integer selecteUserRightAnalyse(WechatUserRight userRight){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT id FROM wechat_user_right WHERE openId = ? AND type = ? AND questionId = ?";
        Object[] args = {userRight.getOpenId(),userRight.getType(),userRight.getQuestionId()};
        return (Integer) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                try {
                    set.getInt("id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }




}
