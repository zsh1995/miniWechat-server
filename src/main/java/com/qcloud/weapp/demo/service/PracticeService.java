package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.dto.QuestionDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public interface PracticeService {

    /**
     * 获取指定6个题目
     * @returnpublic
     */
    List<PracticeRecordDTO> getRecord(String openId, int stars);

    List<QuestionDTO> getDatas(String openId, int stars, int groupId);

    List<QuestionDTO> getExamQuestion(int stars);
    String getQuestionAnalyse(String openId, int id,int star) throws Exception ;



    }
