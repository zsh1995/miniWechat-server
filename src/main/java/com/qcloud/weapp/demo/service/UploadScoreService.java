package main.java.com.qcloud.weapp.demo.service;

import main.java.com.qcloud.weapp.demo.dto.PracticeRecordDTO;

/**
 * Created by Administrator on 2017/6/18.
 */
public interface UploadScoreService {

    boolean uploadScore(String openId, PracticeRecordDTO dto);

}
