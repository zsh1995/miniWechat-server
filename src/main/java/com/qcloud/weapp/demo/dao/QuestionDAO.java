package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.mapper.QuestionMapper;
import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zsh1995 on 2017/6/15.
 */
public class QuestionDAO {

    public List<QuestionDTO> getQuestionWork(String openId,Integer groupId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content , wui.id AS purchId FROM wechat_questions_love wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id LEFT JOIN wechat_user_right wui ON wui.questionId = wqw.id AND wui.star = 3 AND wui.openId = ? WHERE pq.practice_class = 'work' AND pq.group_id = ?";
        Object[] args={groupId};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getQuestionLove(String openId,Integer groupId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content , wui.id AS purchId FROM wechat_questions_love wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id LEFT JOIN wechat_user_right wui ON wui.questionId = wqw.id AND wui.star = 1 AND wui.openId = ? WHERE pq.practice_class = 'love' AND pq.group_id = ?";
        Object[] args={openId,groupId};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getQuestionSocial(String openId,Integer groupId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content , wui.id AS purchId FROM wechat_questions_love wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id LEFT JOIN wechat_user_right wui ON wui.questionId = wqw.id AND wui.star = 2 AND wui.openId = ? WHERE pq.practice_class = 'social' AND pq.group_id = ?";
        Object[] args={groupId};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public Integer getAllCount(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT MAX(id) FROM wechat_questions_work";
        Integer[] args = {};
        int count = (Integer) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                int id = 0;
                try {
                    id = set.getInt("id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return id;
            }
        });
        return count;
    }

    public List<QuestionDTO> getRandQuestionWork(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content FROM wechat_questions_work wqw ORDER BY  RAND() LIMIT 6";
        Object[] args={};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getRandQuestionLove(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content FROM wechat_questions_love wqw ORDER BY  RAND() LIMIT 6";
        Object[] args={};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getRandQuestionSocial(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content FROM wechat_questions_social wqw ORDER BY  RAND() LIMIT 6";
        Object[] args={};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public String getAnalyseLove(int id){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT analysis FROM wechat_questions_love WHERE id = ? ";
        Object[] args={id};
        return (String)optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                try {
                    return set.getString("analysis");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    public String getAnalyseWork(int id){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT analysis FROM wechat_questions_work WHERE id = ? ";
        Object[] args={id};
        return (String)optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                try {
                    return set.getString("analysis");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    public String getAnalyseSocial(int id){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT analysis FROM wechat_questions_social WHERE id = ? ";
        Object[] args={id};
        return (String)optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                try {
                    return set.getString("analysis");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }



}
