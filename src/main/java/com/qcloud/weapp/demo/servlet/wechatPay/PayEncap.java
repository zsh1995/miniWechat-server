package com.qcloud.weapp.demo.servlet.wechatPay;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.common.*;
import com.qcloud.weapp.demo.dao.WechatPayDAO;
import com.qcloud.weapp.demo.entity.PurchAnalyseRecord;
import com.qcloud.weapp.demo.entity.PurchExamRecord;
import com.qcloud.weapp.demo.util.JsonReader;
import com.thoughtworks.xstream.XStream;
import com.qcloud.weapp.demo.dto.OrderInfoDTO;
import com.qcloud.weapp.demo.dto.OrderReturnDTO;
import com.qcloud.weapp.demo.dto.SignInfoDTO;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by zsh1995 on 2017/6/26.
 */
@WebServlet("/pay/payEncap")
public class PayEncap extends HttpServlet{

    Log log = LogFactory.getLog(this.getClass());

    WechatPayDAO payDAO = new WechatPayDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        JSONObject jsonObject = JsonReader.receivePost(request);
        int type = (int) jsonObject.get("type");
        int star = (int) jsonObject.get("star");
        int groupId = 0;
        int questionId = 0;
        if(ApiConst.PURCH_TYPE_ANALYSE == type){
            questionId = (int) jsonObject.get("questionId");
        }
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

        if("".equals(openId)){
            JSONObject result = new JSONObject();
            result.put("code", 1);
            result.put("message", "erro");
        }

        //生成商户订单
        OrderInfoDTO orderInfoDTO = generateOrder(openId,type,star,questionId);
        //调用Wechat下单接口
        OrderReturnDTO orderReturnDTO = prePay(orderInfoDTO);
        //写入数据库
        //todo:写入数据库
        //先从其他DTO获取数据
        PurchExamRecord record = packageRecord(openId,star,orderInfoDTO.getOut_trade_no());
        log.error("Out_trade_no="+record.getTradeNo());
        if(ApiConst.PURCH_TYPE_ANALYSE == type){
            record.setType(ApiConst.RIGHT_TYPE_ANALYSE);
            record.setPurchQuestionId(questionId);
        } else if(ApiConst.PURCH_TYPE_EXAM == type){
            record.setType(ApiConst.RIGHT_TYPE_EXAM);
            record.setPurchStar(star);
        }
        Long id = payDAO.insertNewExamPayRecord(record);
        log.debug("payDao return : " + id);
        //将封装好的数据写库

        //组合数据并签名返回小程序
        response.getWriter().write(
                resignData(orderReturnDTO.getPrepay_id()).toString()
        );

    }

    private OrderInfoDTO generateOrder(String openId,int type,int star,int questionId){
        StringBuilder bodyBuilder = new StringBuilder("高商联盟-");
        int price = 0;
        try {
            OrderInfoDTO order = new OrderInfoDTO();
            order.setAppid(Configure.getAppID());
            order.setMch_id(Configure.getMch_id());
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

            if(ApiConst.PURCH_TYPE_EXAM == type){
                order.setOut_trade_no(generateOutTradeNo(ApiConst.PURCH_TYPE_EXAM,star));
                price = 1;

                switch (star){
                    case 1:bodyBuilder.append("一星级考试");break;
                    case 2:bodyBuilder.append("二星级考试");break;
                    case 3:bodyBuilder.append("三星级考试");break;
                }

            }else if(ApiConst.PURCH_TYPE_ANALYSE == type){
                order.setOut_trade_no(generateOutTradeNo(ApiConst.PURCH_TYPE_ANALYSE,star,0,questionId));
                price = 1;
                bodyBuilder.append("试题解析")
                        .append("No")
                        .append(questionId+",").append(star).append("星");
            }
            order.setBody(bodyBuilder.toString());
            //order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
            order.setTotal_fee(price);
            order.setSpbill_create_ip("118.89.242.169");
            order.setNotify_url("https://74043727.qcloud.la/gslm/weixinpay/PayResult");
            order.setTrade_type("JSAPI");
            order.setOpenid(openId);
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

    private String generateOutTradeNo(int type,int star){
        SimpleDateFormat sdf = new SimpleDateFormat(ApiConst.FORMATE_DATE);
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date()))
                .append("E")
                .append(star)
                .append(RandomStringGenerator.getRandomStringByLength(22));
        return sb.toString();
    }

    private String generateOutTradeNo(int type,int star,int grouId,int questionId){
        SimpleDateFormat sdf = new SimpleDateFormat(ApiConst.FORMATE_DATE);
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date()))
                .append("A")
                .append(star)
                .append(RandomStringGenerator.getRandomStringByLength(22));
        return sb.toString();
    }

    private PurchExamRecord packageRecord(String openId,int star,String tradeNo){
        PurchExamRecord record = new PurchExamRecord();
        record.setOpenId(openId);
        record.setPurchStar(star);
        record.setTradeNo(tradeNo);
        record.setTransaction(0);
        return record;
    }

    private PurchAnalyseRecord packageRecord(String openId,int star,int groupId,int questionId,String tradeNo){
        PurchAnalyseRecord record = new PurchAnalyseRecord();
        record.setOpenId(openId);
        record.setPurchStar(star);
        record.setPurchGroup(groupId);
        record.setQuestionId(questionId);
        record.setTradeNo(tradeNo);
        return record;
    }


    private OrderReturnDTO prePay(OrderInfoDTO orderInfoDTO){
        String result = null;
        try {
            result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", orderInfoDTO);
            System.out.println(result);
            log.error("----openId:"+orderInfoDTO.getOpenid());
            log.info("---------下单返回:"+result);
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
