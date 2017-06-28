package com.qcloud.weapp.demo.mapper;

import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.util.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/6/20.
 */
public class PracticeRecordMapper implements ObjectMapper {
    @Override
    public Object mapping(ResultSet set) {
        PracticeRecordDTO practiceRecordDTO = null;
        try {
            practiceRecordDTO = new PracticeRecordDTO();
            practiceRecordDTO.setStars(set.getInt("stars"));
            practiceRecordDTO.setScore(set.getFloat("score"));
            practiceRecordDTO.setQuestionGroup(set.getInt("question_group"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return practiceRecordDTO;
    }
}
