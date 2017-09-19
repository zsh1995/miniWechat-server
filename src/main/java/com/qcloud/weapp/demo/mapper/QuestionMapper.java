package com.qcloud.weapp.demo.mapper;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.util.ObjectMapper;

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
                questionDTO.setQuestionId((int) set.getLong("id"));
                questionDTO.setContent(set.getString("question_content"));
                questionDTO.setTips(set.getString("tip"));
                String analyseContent = set.getString("analysis");
                if("".equals(analyseContent) || analyseContent == null)
                    questionDTO.setAnalyse("");
                else
                    questionDTO.setAnalyse("it has content");

                questionDTO.setType(set.getString("type"));
                if(ApiConst.TEST_SHENHE){
                    questionDTO.setIsPurchAnalyse(1);
                }else {
                    questionDTO.setIsPurchAnalyse(safeResultGet(set,"purchId") == null ? 0:1);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionDTO;
    }

    private Object safeResultGet(ResultSet set, String columnLabel){
        Object obj = null;
        try {
            obj = set.getObject(columnLabel);
        } catch (SQLException e) {
            obj = null;
        }
        return obj;
    }
}
