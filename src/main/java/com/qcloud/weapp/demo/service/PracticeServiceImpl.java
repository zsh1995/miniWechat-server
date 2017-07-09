package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.dao.QuestionDAO;
import com.qcloud.weapp.demo.dao.UploadScoreDAO;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class PracticeServiceImpl implements PracticeService {
    @Override
    public List<PracticeRecordDTO> getRecord(String openId , int stars) {
        UploadScoreDAO uploadScoreDAO = new UploadScoreDAO();
        PracticeRecordDTO practiceRecordDTO= new PracticeRecordDTO();
        UserInfoDTO dto = uploadScoreDAO.findIdByOpenId(openId);
        return uploadScoreDAO.getScore(dto.getId(),stars);
    }

    @Override
    public List<QuestionDTO> getDatas(int stars, int groupId) {
        QuestionDAO questionDAO = new QuestionDAO();

        if(1 == stars){
            return questionDAO.getQuestionLove(groupId);
        }
        if(2 == stars){
            return questionDAO.getQuestionWork(groupId);
        }
        if(3 == stars){
            return questionDAO.getQuestionSocial(groupId);
        }else {
            return new ArrayList<QuestionDTO>();
        }

    }

    @Override
    public List<QuestionDTO> getExamQuestion(int stars) {
        QuestionDAO questionDAO = new QuestionDAO();
        List<QuestionDTO> questions = new ArrayList<>();
        switch (stars) {
            case 1:
                questions = questionDAO.getRandQuestionLove();
            case 2:
                questions = questionDAO.getRandQuestionSocial();
            case 3:
                questions = questionDAO.getRandQuestionWork();
        }
        return questions;
    }
}
