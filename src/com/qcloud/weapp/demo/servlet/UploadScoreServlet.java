package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.service.UploadScoreService;
import com.qcloud.weapp.demo.service.UploadScoreServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/17.
 */
@WebServlet("/uploadScore")
public class UploadScoreServlet extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        UploadScoreService uploadScoreService = new UploadScoreServiceImpl();
        try {
            UserInfo userInfo  = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);

            String score = (String)jsonObject.get("score");
            float fScore = score != null?Float.parseFloat(score) : 0;
            String groupId = (String) jsonObject.get("groud_id");
            int iGroupId = groupId != null?Integer.parseInt(groupId) : 0;
            int stars = jsonObject.getInt("stars");
            PracticeRecordDTO dto = new PracticeRecordDTO();
            dto.setQuestionGroup(iGroupId);
            dto.setStars(stars);
            dto.setScore(fScore);
            uploadScoreService.uploadScore(userInfo.getOpenId(),dto);
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

    }

}
