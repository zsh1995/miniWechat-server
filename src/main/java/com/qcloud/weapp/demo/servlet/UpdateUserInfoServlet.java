package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.service.UserInfoService;
import com.qcloud.weapp.demo.service.UserInfoServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/2.
 */
@WebServlet("/userInfo/updateUserInfo")
public class UpdateUserInfoServlet extends HttpServlet {

    UserInfoService userInfoService = new UserInfoServiceImpl();
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        try {
            UserInfo userInfo  = service.check();
            userInfoDTO.setOpenId(userInfo.getOpenId());
            JSONObject jsonObject = JsonReader.receivePost(request);
            userInfoDTO.setPhoneNumber(jsonObject.getString("userMobile"));
            userInfoDTO.setMajor(jsonObject.getString("major"));
            userInfoDTO.setSchool(jsonObject.getString("school"));
            userInfoDTO.setStudent_name(jsonObject.getString("userName"));
            userInfoService.updateUserInfo(userInfoDTO);
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

    }

    }
