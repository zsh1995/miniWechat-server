package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.ApiConst;
import com.qcloud.weapp.demo.service.LoginServiceImpl;
import com.qcloud.weapp.demo.service.coupon.CouponService;
import com.qcloud.weapp.demo.service.coupon.CouponServiceImpl;
import com.qcloud.weapp.demo.util.OptTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
       
	private static final long serialVersionUID = 6585319986631669934L;


	/**
	 * 处理登录请求
	 * */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 通过 ServletRequest 和 ServletResponse 初始化登录服务
		LoginService service = new LoginService(request, response);
		CouponService couponService = new CouponServiceImpl();
		com.qcloud.weapp.demo.service.LoginService loginService= new LoginServiceImpl();
		Log log = LogFactory.getLog(this.getClass());
		try {
			// 调用登录接口，如果登录成功可以获得登录信息
			log.error("userInfo:start");
			UserInfo userInfo = service.login();

			//赠送券
			if(!loginService.isExist(userInfo)) {
				if (!couponService.bindAnalyseCoupon(userInfo.getOpenId())) {
					//doing - nothing ....
				}
			}
			//
			loginService.doLogin(userInfo);

			System.out.println("========= LoginSuccess, UserInfo: ==========");
			System.out.println(userInfo.toString());
			log.error("userInfo:"+userInfo.toString());
		} catch (LoginServiceException e) {
			// 登录失败会抛出登录失败异常
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// SDK 如果还没有配置会抛出配置异常
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

}
