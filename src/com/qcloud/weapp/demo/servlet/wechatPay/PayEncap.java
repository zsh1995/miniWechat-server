package com.qcloud.weapp.demo.servlet.wechatPay;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.Configure;
import com.qcloud.weapp.demo.common.RandomStringGenerator;
import com.qcloud.weapp.demo.common.Signature;
import com.qcloud.weapp.demo.dto.OrderInfoDTO;
import com.qcloud.weapp.demo.dto.OrderReturnDTO;
import com.qcloud.weapp.demo.dto.SignInfoDTO;
import com.sun.deploy.net.HttpRequest;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by zsh1995 on 2017/6/26.
 */
@WebServlet("/pay/payEncap")
public class PayEncap extends HttpServlet{

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取openId
        LoginService service = new LoginService(request, response);
        String openId = "";
        try {
            UserInfo userInfo = service.check();
            openId = userInfo.getOpenId();
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        //生成商户订单
        OrderInfoDTO orderInfoDTO = generateOrder(openId);
        //调用Wechat下单接口
        OrderReturnDTO orderReturnDTO = prePay(orderInfoDTO);
        //组合数据并签名返回小程序
        response.getWriter().write(
                resignData(orderReturnDTO.getPrepay_id()).toString()
        );

    }

    private OrderInfoDTO generateOrder(String openId){
        try {
            OrderInfoDTO order = new OrderInfoDTO();
            order.setAppid(Configure.getAppID());
            order.setMch_id(Configure.getMch_id());
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setBody("dfdfdf");
            order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
            order.setTotal_fee(10);
            order.setSpbill_create_ip("123.57.218.54");
            order.setNotify_url("https://www.see-source.com/weixinpay/PayResult");
            order.setTrade_type("JSAPI");
            order.setOpenId(openId);
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            return order;
            //response.getWriter().append(json.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private OrderReturnDTO prePay(OrderInfoDTO orderInfoDTO){
        String result = null;
        try {
            result = com.qcloud.weapp.demo.common.HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", orderInfoDTO);
            System.out.println(result);
            //L.info("---------下单返回:"+result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnDTO.class);
            OrderReturnDTO returnInfo = (OrderReturnDTO)xStream.fromXML(result);
            return returnInfo;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject resignData(String repayId){
        try {
            String repay_id = repayId;
            SignInfoDTO signInfo = new SignInfoDTO();
            signInfo.setAppId(Configure.getAppID());
            long time = System.currentTimeMillis()/1000;
            signInfo.setTimeStamp(String.valueOf(time));
            signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
            signInfo.setRepay_id("prepay_id="+repay_id);
            signInfo.setSignType("MD5");
            //生成签名
            String sign = Signature.getSign(signInfo);
            JSONObject json = new JSONObject();
            json.put("timeStamp", signInfo.getTimeStamp());
            json.put("nonceStr", signInfo.getNonceStr());
            json.put("package", signInfo.getRepay_id());
            json.put("signType", signInfo.getSignType());
            json.put("paySign", sign);
            return  json;
            //L.info("-------再签名:"+json.toJSONString());
            //response.getWriter().append(json.toJSONString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //L.error("-------", e);
        }
        return null;
    }






}
