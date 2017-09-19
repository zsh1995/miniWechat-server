package com.qcloud.weapp.demo.servlet.Coupon;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiMethod;
import com.qcloud.weapp.demo.service.coupon.CouponService;
import com.qcloud.weapp.demo.service.coupon.CouponServiceImpl;
import com.qcloud.weapp.demo.util.AnalyseCoupon;
import com.qcloud.weapp.demo.util.CouponUse;
import com.qcloud.weapp.demo.util.ExamCoupon;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/25.
 */
@WebServlet("/coupon/useCoupon")
public class UseCouponServlet extends HttpServlet {

    private CouponService couponService = new CouponServiceImpl();

    private CouponUse analyseCoupon = new AnalyseCoupon();

    private CouponUse examCoupon = new ExamCoupon();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        LoginService service = new LoginService(request, response);
        JSONObject result = new JSONObject();
        try {
            UserInfo userInfo = service.check();

            JSONObject jsonData = JsonReader.receivePost(request);

            int questionId ;
            int star = (int) jsonData.get("star");
            int couponId = (int) jsonData.get("couponId");
            do {
                if (couponId == 1) {
                    questionId = (int) jsonData.get("questionId");
                    Map dataMap = new HashMap<>();
                    dataMap.put("questionId", questionId);
                    dataMap.put("star", star);
                    if (!analyseCoupon.useCoupon(userInfo.getOpenId(), dataMap)) {
                        throw new Exception("使用券失败");
                    }
                    break;
                }
                if (couponId == 2){
                    Map dataMap = new HashMap<>();
                    dataMap.put("star", star);
                    if (!examCoupon.useCoupon(userInfo.getOpenId(), dataMap)) {
                        throw new Exception("使用券失败");
                    }
                    break;
                }
            } while(false);


            ApiMethod.formateResultWithNothing(result,true);

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
