package main.java.com.qcloud.weapp.demo.dao;

import main.java.com.qcloud.weapp.demo.dto.QuestionDTO;
import main.java.com.qcloud.weapp.demo.mapper.QuestionMapper;
import main.java.com.qcloud.weapp.demo.util.ObjectMapper;
import main.java.com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zsh1995 on 2017/6/15.
 */
public class QuestionDAO {

    public List<QuestionDTO> getQuestionWork(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content FROM wechat_questions_work wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id WHERE pq.practice_class = 'work'";
        Object[] args={};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getQuestionLove(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content FROM wechat_questions_love wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id WHERE pq.practice_class = 'love'";
        Object[] args={};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getQuestionSocial(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT wqw.id, wqw.type, wqw.tip, wqw.analysis, wqw.question_content FROM wechat_questions_social wqw LEFT JOIN practice_question pq ON pq.question_1 = wqw.id OR pq.question_1 = wqw.id OR pq.question_2 = wqw.id OR pq.question_3 = wqw.id OR pq.question_4 = wqw.id OR pq.question_5 = wqw.id OR pq.question_6 = wqw.id WHERE pq.practice_class = 'social'";
        Object[] args={};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public Integer getAllCount(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT MAX(id) FROM wechat_questions_work";
        Integer[] args = {};
        int count = (int)optTemplate.find(sql, args, new ObjectMapper() {
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

}
