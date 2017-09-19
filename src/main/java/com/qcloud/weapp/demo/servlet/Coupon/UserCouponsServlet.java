package com.qcloud.weapp.demo.servlet.Coupon;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.service.PracticeService;
import com.qcloud.weapp.demo.service.PracticeServiceImpl;
import com.qcloud.weapp.demo.service.coupon.CouponService;
import com.qcloud.weapp.demo.service.coupon.CouponServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
@WebServlet("/coupon/userCoupon")
public class UserCouponsServlet extends HttpServlet {

    private CouponService couponService = new CouponServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        LoginService service = new LoginService(request, response);
        JSONObject result = new JSONObject();
        try {
            UserInfo userInfo = service.check();

            List couponList = couponService.userAllCoupons(userInfo.getOpenId());

            ApiMethod.formateResultWithDate(result,couponList);

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
