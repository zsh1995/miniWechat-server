package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.service.UserInfoService;
import com.qcloud.weapp.demo.service.UserInfoServiceImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10.
 */
@WebServlet("/userInfo/getInvitedList")
public class getInvitedList extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

        LoginService loginService = new LoginService(request,response);
        UserInfoService userInfoService = new UserInfoServiceImpl();
        JSONObject result = new JSONObject();
        try {
            Map dataMap = new HashMap();
            UserInfo userInfo = loginService.check();
            List<Map> invitedList = userInfoService.getAllInvite(userInfo.getOpenId());
            Map invitorInfo = userInfoService.getMyInvitor(userInfo.getOpenId());
            dataMap.put("myInvitor",invitorInfo);
            dataMap.put("invitedList",invitedList);
            ApiMethod.formateResultWithDate(result,dataMap);
        } catch (LoginServiceException e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        } catch (ConfigurationException e) {
            ApiMethod.formateResultWithExcp(result,e);
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result.toString());



    }
    }
