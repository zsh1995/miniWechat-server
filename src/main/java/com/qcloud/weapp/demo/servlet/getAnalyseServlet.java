package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.service.PracticeService;
import com.qcloud.weapp.demo.service.PracticeServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/8.
 */
@WebServlet("/question/getAnalyse")
public class getAnalyseServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        LoginService service = new LoginService(request, response);
        PracticeService practiceService = new PracticeServiceImpl();
        JSONObject result = new JSONObject();
        try {
            UserInfo userInfo = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);
            int star = (int) jsonObject.get("star");
            int questionId = (int) jsonObject.get("id");
            String analyseContent = practiceService.getQuestionAnalyse(userInfo.getOpenId(),questionId,star);
            ApiMethod.formateResultWithDate(result,analyseContent);
        } catch (LoginServiceException e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        } catch (ConfigurationException e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        } catch (Exception e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result.toString());
    }

}
