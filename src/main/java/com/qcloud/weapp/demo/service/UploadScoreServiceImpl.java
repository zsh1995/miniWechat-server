package main.java.com.qcloud.weapp.demo.service;

import main.java.com.qcloud.weapp.demo.dao.UploadScoreDAO;
import main.java.com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import main.java.com.qcloud.weapp.demo.dto.UserInfoDTO;

/**
 * Created by Administrator on 2017/6/18.
 */
public class UploadScoreServiceImpl implements UploadScoreService {
    @Override
    public boolean uploadScore(String openId, PracticeRecordDTO practiceRecordDTO) {
        UploadScoreDAO uploadScoreDAO = new UploadScoreDAO();
        UserInfoDTO dto = uploadScoreDAO.findIdByOpenId(openId);
        practiceRecordDTO.setUserId(dto.getId());
        return uploadScoreDAO.insertNewScore(practiceRecordDTO);
    }
}
