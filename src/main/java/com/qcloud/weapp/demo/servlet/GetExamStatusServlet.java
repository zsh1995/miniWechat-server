package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.service.UploadScoreService;
import com.qcloud.weapp.demo.service.UploadScoreServiceImpl;
import com.qcloud.weapp.demo.service.uerRight.UserRightService;
import com.qcloud.weapp.demo.service.uerRight.UserRightServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/14.
 */
@WebServlet("/exam/getExamStatus")
public class GetExamStatusServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        UserRightService userRightService = new UserRightServiceImpl();
        JSONObject result = new JSONObject();
        try {
            UserInfo userInfo  = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);
            int stars = jsonObject.getInt("stars");
            int examStatus = userRightService.getExamStatus(userInfo.getOpenId(),stars);
            Map dataMap = new HashMap();
            dataMap.put("examStatus",examStatus);
            ApiMethod.formateResultWithDate(result,dataMap);

        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            ApiMethod.formateResultWithExcp(result,e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result.toString());

    }

}
