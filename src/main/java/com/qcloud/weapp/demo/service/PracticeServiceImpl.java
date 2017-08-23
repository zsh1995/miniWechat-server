package com.qcloud.weapp.demo.service;

import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.dao.QuestionDAO;
import com.qcloud.weapp.demo.dao.UploadScoreDAO;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.service.uerRight.UserRightService;
import com.qcloud.weapp.demo.service.uerRight.UserRightServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class PracticeServiceImpl implements PracticeService {
    UserRightService userRightService = new UserRightServiceImpl();
    QuestionDAO questionDAO = new QuestionDAO();
    @Override
    public List<PracticeRecordDTO> getRecord(String openId , int stars) {
        UploadScoreDAO uploadScoreDAO = new UploadScoreDAO();
        PracticeRecordDTO practiceRecordDTO= new PracticeRecordDTO();
        UserInfoDTO dto = uploadScoreDAO.findIdByOpenId(openId);
        return uploadScoreDAO.getScore(dto.getId(),stars);
    }

    @Override
    public List<QuestionDTO> getDatas(String openId,int stars, int groupId) {
        QuestionDAO questionDAO = new QuestionDAO();

        if(1 == stars){
            return questionDAO.getQuestionLove(openId,groupId);
        }
        if(2 == stars){
            return questionDAO.getQuestionSocial(openId,groupId);
        }
        if(3 == stars){
            return questionDAO.getQuestionWork(openId,groupId);
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
                questions = questionDAO.getRandQuestionLove();break;
            case 2:
                questions = questionDAO.getRandQuestionSocial();break;
            case 3:
                questions = questionDAO.getRandQuestionWork();break;
        }
        return questions;
    }



    @Override
    public String getQuestionAnalyse(String openId, int id,int star) throws Exception {
        //校验是否有改题权限
        //如果没有跑出异常
        String analyseContent = "";
        int rightId = userRightService.checkUserRight(openId, ApiConst.PURCH_TYPE_ANALYSE,star,id);
        if(0 == rightId){
            throw new Exception("没有权限！");
        }
        switch (star) {
            case 1:
                analyseContent = questionDAO.getAnalyseLove(id);break;
            case 2:
                analyseContent = questionDAO.getAnalyseWork(id);break;
            case 3:
                analyseContent = questionDAO.getAnalyseSocial(id);break;
        }
        return analyseContent;

    }
}
