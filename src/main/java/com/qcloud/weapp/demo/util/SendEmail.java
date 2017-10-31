package com.qcloud.weapp.demo.util;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by mrzsh on 2017/10/31.
 */
public class SendEmail extends Thread {

    private static final Logger L = Logger.getLogger(SendEmail.class);

    private String topic;

    private String text;

    private Object[] data;

    private static final String ERRO_TOPIC="错误报警";

    public SendEmail(String topic,String text,Object[] data){
        this.topic = topic;
        this.text = text;
        this.data = data;
    }

    private void sendErroEmail(Object[] data){
        EmailUtil emailUtil = EmailUtil.getInstance();
        topic = ERRO_TOPIC;
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        sb.append("<div>出错数据为：<p style=\"color:red\">");
        for (Object item:
             data) {
            sb.append(item).append(",<br/>");
        }
        sb.append("</p></div>")
                .append("<div>发生时间是")
                .append(new Date())
                .append("</div>");

        emailUtil.sendEmail(topic,sb.toString());
    }
    @Override
    public void run(){
        try {
            sendErroEmail(data);
        }catch (RuntimeException e){
            L.error("send email thread erro",e);
        }
    }

    public static void main(String[] args){
        Thread threa1 = new SendEmail("测试1","多线程测试", new String[]{"1", "2"});
        threa1.start();
    }


}
