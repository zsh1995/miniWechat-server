package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.service.PracticeService;
import com.qcloud.weapp.demo.service.PracticeServiceImpl;
import com.qcloud.weapp.demo.service.uerRight.UserRightService;
import com.qcloud.weapp.demo.service.uerRight.UserRightServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */
@WebServlet("/exam/getExamQuestions")
public class GetExamQuestionsServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        UserRightService userRightService = new UserRightServiceImpl();
        PracticeService practiceService = new PracticeServiceImpl();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            UserInfo userInfo = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);
            int type = (int) jsonObject.get("type");
            int star = (int) jsonObject.get("star");
            int questionId = (int) jsonObject.get("questionId");
            int remainTimes = userRightService.checkUserRight(userInfo.getOpenId(),type,star,questionId);
            if(remainTimes > 0){
                userRightService.updateUserRight(userInfo.getOpenId(),star,remainTimes - 1);
            } else {
                result.put("code", 1);
                result.put("message", "erro:user right erro");
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(result.toString());
                return;
            }
            List<QuestionDTO> questions= practiceService.getExamQuestion(star);

            JSONArray listArray = JSONArray.fromObject(questions);
            data.put("questionList",listArray);
            result.put("code", 0);
            result.put("message", "OK");
            result.put("data", data);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
