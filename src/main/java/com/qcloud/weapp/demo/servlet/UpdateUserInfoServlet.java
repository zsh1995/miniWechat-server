package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.dto.UserInfoDTO;
import com.qcloud.weapp.demo.service.UserInfoService;
import com.qcloud.weapp.demo.service.UserInfoServiceImpl;
import com.qcloud.weapp.demo.service.company.CompanyService;
import com.qcloud.weapp.demo.service.company.CompanyServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

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
    
    public static Logger L = Logger.getLogger(UpdateUserInfoServlet.class);
    
    String saveGet(JSONObject jsonObject,String key){
        try{
            return jsonObject.getString(key);
        }catch (Exception e){
            return "";
        }
    }

    UserInfoService userInfoService = new UserInfoServiceImpl();
    CompanyService companyService = new CompanyServiceImpl();
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        try {
            UserInfo userInfo  = service.check();
            userInfoDTO.setOpenId(userInfo.getOpenId());
            JSONObject jsonObject = JsonReader.receivePost(request);
            userInfoDTO.setPhoneNumber(saveGet(jsonObject,"userMobile"));
            userInfoDTO.setMajor(saveGet(jsonObject,"major"));
            userInfoDTO.setSchool(saveGet(jsonObject,"school"));
            userInfoDTO.setStudent_name(saveGet(jsonObject,"userName"));
            userInfoDTO.setPost(saveGet(jsonObject,"post"));
            userInfoDTO.setType(saveGet(jsonObject,"type"));
            userInfoDTO.setCity(saveGet(jsonObject,"city"));
            userInfoDTO.setGender(saveGet(jsonObject,"gender"));
            userInfoDTO.setCompany(saveGet(jsonObject,"company"));
            userInfoDTO.setWanted_company1(saveGet(jsonObject,"wanted_company1"));
            userInfoDTO.setWanted_company2(saveGet(jsonObject,"wanted_company2"));
            userInfoDTO.setWanted_company3(saveGet(jsonObject,"wanted_company3"));
            userInfoDTO.setWanted_company4(saveGet(jsonObject,"wanted_company4"));
            userInfoDTO.setWanted_company5(saveGet(jsonObject,"wanted_company5"));
            userInfoDTO.setEmailAddr(saveGet(jsonObject,"emailAddr"));

            userInfoService.updateUserInfo(userInfoDTO);
        } catch (LoginServiceException e) {
            L.error("更新数据出错",e);
            e.printStackTrace();
        } catch (ConfigurationException e) {
            L.error("更新数据出错",e);
            e.printStackTrace();
        } catch (Exception e) {
            L.error("更新数据出错",e);
            e.printStackTrace();
        }

    }

    }
