package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import com.qcloud.weapp.demo.service.UploadScoreService;
import com.qcloud.weapp.demo.service.UploadScoreServiceImpl;
import com.qcloud.weapp.demo.service.UserInfoService;
import com.qcloud.weapp.demo.service.UserInfoServiceImpl;
import com.qcloud.weapp.demo.service.coupon.CouponService;
import com.qcloud.weapp.demo.service.coupon.CouponServiceImpl;
import com.qcloud.weapp.demo.util.ExamCoupon;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */
@WebServlet("/userInfo/setInvitor")
public class UploadInvitorServlet extends HttpServlet {

    private CouponService couponService = new CouponServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        UserInfoService uploadScoreService = new UserInfoServiceImpl();
        ExamCoupon examCoupon = new ExamCoupon();
        JSONObject result = new JSONObject();
        try {
            UserInfo userInfo  = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);

            String invitorId = (String)jsonObject.get("invitorId");
            long lonInvitorId = invitorId != null?Long.parseLong(invitorId) : 0;
            Map userData = uploadScoreService.getInvitor(lonInvitorId);
            if( userData == null){
                throw new Exception("不存在用户");
            }
            if(userData.get("openId").equals(userInfo.getOpenId())){
                throw new Exception("不能选择自己");
            }

            couponService.bindExamCoupon(userInfo.getOpenId());


            uploadScoreService.updateUserInvitor(lonInvitorId,userInfo.getOpenId());
            ApiMethod.formateResultWithNothing(result,true);
        } catch (LoginServiceException e) {
            e.printStackTrace();
            ApiMethod.formateResultWithExcp(result,e);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            ApiMethod.formateResultWithExcp(result,e);
        }catch (Exception e){
            e.printStackTrace();
            ApiMethod.formateResultWithExcp(result,e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result.toString());
    }

}
