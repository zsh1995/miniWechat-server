package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.service.uerRight.UserRightService;
import com.qcloud.weapp.demo.service.uerRight.UserRightServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/14.
 */
@WebServlet("/exam/uploadStatus")
public class UploadExamStatusServlet extends HttpServlet {
    UserRightService userRightService = new UserRightServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        JSONObject result = new JSONObject();
        try {
            // 调用检查登录接口，成功后可以获得用户信息，进行正常的业务请求
            UserInfo userInfo = service.check();
            net.sf.json.JSONObject jsonObject = JsonReader.receivePost(request);
            int star = (int) jsonObject.get("star");
            userRightService.updateUserExamStatus(userInfo.getOpenId(),
                    "exam",
                    star,
                    0
                    );
            // 获取会话成功，输出获得的用户信息
            JSONObject data = new JSONObject();
            data.put("userInfo", new JSONObject(userInfo));
            result.put("code", 0);
            result.put("message", "OK");
            result.put("data", data);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        response.getWriter().write(result.toString());
    }
}
