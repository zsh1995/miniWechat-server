package com.qcloud.weapp.demo.servlet.wechatPay;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.service.UserInfoService;
import com.qcloud.weapp.demo.service.UserInfoServiceImpl;
import com.qcloud.weapp.demo.service.payService.Payservice;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
@WebServlet("/pay/getPurchRecord")
public class GetPurchRecord extends HttpServlet {

    UserInfoService userInfoService = new UserInfoServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        JSONObject result = new JSONObject();

        try {
            UserInfo userInfo = service.check();

            if(userInfo.getOpenId() == null) {
                throw new Exception("openId is null");
            }
            List<Map> purchData = userInfoService.getPurchExamRecord(userInfo.getOpenId());
            ApiMethod.formateResultWithDate(result,purchData);
        } catch (LoginServiceException e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        } catch (ConfigurationException e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        } catch (Exception e){
            ApiMethod.formateResultWithExcp(result,e);
        }
        response.getWriter().write(result.toString());


    }
}
