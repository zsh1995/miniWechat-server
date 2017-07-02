package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.service.PracticeService;
import com.qcloud.weapp.demo.service.PracticeServiceImpl;
import com.qcloud.weapp.demo.service.UserInfoService;
import com.qcloud.weapp.demo.service.UserInfoServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */
@WebServlet("/userInfo/getUserInfo")
public class GetUserInfoServlet extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log log = LogFactory.getLog(this.getClass());
        LoginService service = new LoginService(request, response);
        UserInfoService userInfoService = new UserInfoServiceImpl();
        try {
            UserInfo userInfo = service.check();
            UserInfoDTO userInfoDTO = userInfoService.getUserInfo(userInfo.getOpenId());

            JSONObject result = new JSONObject();
            JSONObject data = new JSONObject();
            JSONObject listArray = JSONObject.fromObject(userInfoDTO);
            data.put("userInfo",listArray);
            log.error("userInfo is :"+listArray);
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
        }
    }
}
