package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.mapper.PracticeRecordMapper;
import com.qcloud.weapp.demo.mapper.UserInfoMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class UploadScoreDAO {

    public UserInfoDTO findIdByOpenId(String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT id FROM wechat_userinfo WHERE openid = ?";
        String[] args = {openId};
        return (UserInfoDTO) optTemplate.find(sql,args,new UserInfoMapper());
    }

    public boolean insertNewScore(PracticeRecordDTO dto){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO practice_record(user_id,question_group,score,stars,create_date) VALUE(?,?,?,?,NOW()) ON DUPLICATE KEY UPDATE user_id= ?,question_group=?,score=?,stars=?,create_date=NOW()";
        Object[] args = {dto.getUserId(),dto.getQuestionGroup(),dto.getScore(),dto.getStars(),dto.getUserId(),dto.getQuestionGroup(),dto.getScore(),dto.getStars()};
        return optTemplate.update(sql,args,false);
    }

    public List<PracticeRecordDTO> getScore(Integer id,Integer stars){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT * FROM practice_record WHERE user_id = ? AND stars = ?";
        Object[] args = {id,stars};
        return (List<PracticeRecordDTO>) optTemplate.query(sql,args,new PracticeRecordMapper());
    }

}
