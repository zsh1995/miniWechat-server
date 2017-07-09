package com.qcloud.weapp.demo.servlet.wechatPay;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
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

/**
 * Created by Administrator on 2017/7/8.
 */
@WebServlet("/pay/checkPurchRecord")
public class CheckPurchRecordServlet extends HttpServlet{


    /**
     * in   :
     *        type
     *        star
     *        questionId
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
        LoginService service = new LoginService(request, response);
        UserRightService userRightService = new UserRightServiceImpl();
        //
        try {
            UserInfo userInfo = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);
            int type = (int) jsonObject.get("type");
            int star = (int) jsonObject.get("star");
            int questionId = (int) jsonObject.get("questionId");
            int remainTimes = userRightService.checkUserRight(userInfo.getOpenId(),type,star,questionId);
            JSONObject result = new JSONObject();
            JSONObject data = new JSONObject();
            result.put("code", 0);
            result.put("message", "OK");
            data.put("remainTime",remainTimes);
            result.put("data",data);
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
