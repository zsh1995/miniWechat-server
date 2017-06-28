package main.java.com.qcloud.weapp.demo.mapper;

import main.java.com.qcloud.weapp.demo.dto.QuestionDTO;
import main.java.com.qcloud.weapp.demo.util.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zsh1995 on 2017/6/16.
 */
public class QuestionMapper implements ObjectMapper {
    @Override
    public Object mapping(ResultSet set) {
        QuestionDTO questionDTO = null;
        try {
                questionDTO = new QuestionDTO();
                questionDTO.setContent(set.getString("question_content"));
                questionDTO.setTips(set.getString("tip"));
                questionDTO.setAnalyse(set.getString("analysis"));
                questionDTO.setType(set.getString("type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionDTO;
    }
}
