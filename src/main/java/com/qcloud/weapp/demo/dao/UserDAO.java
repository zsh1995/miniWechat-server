package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.entity.WechatUserRight;
import com.qcloud.weapp.demo.mapper.UserInfoMapper;
import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "SELECT wu1.id,wu1.student_name,wu1.school,wu1.major,wu1.phone_number,wu1.post,wu1.type,wu1.city,wu1.gender,wu1.company,wu1.wanted_company,IFNULL(wu2.student_name,wu2.wechat_nick) as invitor, wu1.emailAddr FROM wechat_userinfo wu1 LEFT JOIN wechat_userinfo wu2 ON wu2.id = wu1.invitor WHERE wu1.openid = ?";
        String[] args = {openId};
        return (UserInfoDTO) optTemplate.find(sql,args,new UserInfoMapper());
    }

    public Map selectInvitor(Long id){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT avatar_url ,IFNULL(student_name,wechat_nick) as name FROM wechat_userinfo WHERE id = ?";
        Long[] args = {id};
        return (Map) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                Map map = new HashMap();
                try {
                    map.put("avatar_url",set.getString("avatar_url"));
                    map.put("name",set.getString("name"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return map;
            }
        });
    }

    public boolean updateInvitor(Long id,String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE wechat_userinfo SET invitor = ? WHERE openId = ?";
        Object[] args = {id,openId};
        return optTemplate.update(sql,args,false);
    }

    public boolean updateUserInfo(UserInfoDTO userInfo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE wechat_userinfo\n" +
                "SET student_name =?,school =?,major = ?,phone_number = ?,post= ?,type= ?,city= ?,gender= ?,company= ?,wanted_company= ?,emailAddr=?\n" +
                "WHERE\n" +
                "	openId =?";
        String[] args = {userInfo.getStudent_name(),userInfo.getSchool(),userInfo.getMajor(),userInfo.getPhoneNumber(),
                userInfo.getPost(),userInfo.getType(),userInfo.getCity(),userInfo.getGender(),userInfo.getCompany(),userInfo.getWanted_company(),userInfo.getEmailAddr(),
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

    public List<Map> queryAllInvite(String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT IFNULL(invitedWU.student_name, invitedWU.wechat_nick) AS `name`, invitedWU.avatar_url FROM wechat_userinfo invitedWU LEFT JOIN wechat_userinfo invitorWU ON invitorWU.id = invitedWU.invitor WHERE invitorWU.openId = ?";
        Object[] args = {openId};
        return (List<Map>) optTemplate.query(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                Map map = new HashMap();
                try {
                    map.put("avatar_url",set.getString("avatar_url"));
                    map.put("name",set.getString("name"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return map;
            }
        });
    }

    public Long selecteUserRightAnalyse(WechatUserRight userRight){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT id FROM wechat_user_right WHERE openId = ? AND type = ? AND questionId = ?";
        Object[] args = {userRight.getOpenId(),userRight.getType(),userRight.getQuestionId()};
        return (Long) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                try {
                    return set.getLong("id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


}
