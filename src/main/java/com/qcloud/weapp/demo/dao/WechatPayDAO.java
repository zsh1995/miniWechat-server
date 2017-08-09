package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.common.MapperTools;
import com.qcloud.weapp.demo.dto.weixinPay.WechatResult;
import com.qcloud.weapp.demo.entity.PurchExamRecord;
import com.qcloud.weapp.demo.entity.WeixinReturnStatements;
import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/6.
 */
public class WechatPayDAO {

    public boolean insertNewPayReturn(WeixinReturnStatements returnData){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO weixin_return_statements (\n" +
                "\t`appid`,\n" +
                "\t`bank_type`,\n" +
                "\t`fee_type`,\n" +
                "\t`mch_id`,\n" +
                "\t`nonce_str`,\n" +
                "\t`openid`,\n" +
                "\t`out_trade_no`,\n" +
                "\t`result_code`,\n" +
                "\t`return_code`,\n" +
                "\t`sign`,\n" +
                "\t`time_end`,\n" +
                "\t`total_fee`,\n" +
                "\t`trade_type`,\n" +
                "\t`transaction_id`\n" +
                ")\n" +
                "VALUE\n" +
                "\t(\n" +
                "\t\t?,?,?,?,?,?,?,?,?,?,?,?,?,?\n" +
                "\t)";
        Object[] args = {returnData.getAppid(),returnData.getBankType(),returnData.getFeeType()
                ,returnData.getMchId(),returnData.getNonceStr(),returnData.getOpenid(),returnData.getOutTradeNo(),returnData.getResultCode()
                ,returnData.getReturnCode(),returnData.getSign(),returnData.getTimeEnd(),returnData.getTotalFee()
                ,returnData.getTradeType(),returnData.getTransactionId()};
        return optTemplate.update(sql,args,false);
    }

    public Long insertNewExamPayRecord(PurchExamRecord purchExamRecord){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "INSERT INTO purch_exam_record (openId,purch_star,type,trade_no,transaction,create_date,purch_question_id) VALUE (?,?,?,?,?,NOW(),?)";
        Object[] args = {purchExamRecord.getOpenId(),purchExamRecord.getPurchStar(),purchExamRecord.getType(),purchExamRecord.getTradeNo()
                ,purchExamRecord.getTransaction(),purchExamRecord.getPurchQuestionId()};
        if(optTemplate.update(sql,args,true)){
            return (Long) args[0];
        }
        return null;
    }



    public boolean setExamPayRecordStatus(PurchExamRecord purchExamRecord){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE purch_exam_record SET `transaction` = ?,remain_times=?    WHERE trade_no = ? ";
        Object[] args={purchExamRecord.getTransaction(),purchExamRecord.getRemainTimes(),purchExamRecord.getTradeNo()};
        return optTemplate.update(sql,args,false);
    }

    public PurchExamRecord getExamPayRecordByTradeNo(String tradeNo){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "UPDATE purch_exam_record SET `transaction` = ?,remain_times=?    WHERE trade_no = ? ";
        Object[] args={tradeNo};
        return (PurchExamRecord) optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                PurchExamRecord purchExamRecord = (PurchExamRecord) MapperTools.rsMapEntity(PurchExamRecord.class,set);
                return purchExamRecord;
            }
        });
    }

    public List getPurchExamRecord(String openId){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT type,purch_question_id as questionId,purch_star,UNIX_TIMESTAMP(create_date) as date FROM purch_exam_record WHERE openId= ? AND delete_flag != 1 AND `transaction` = 1";
        Object[] args={openId};
        return optTemplate.query(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                Map data = new HashMap();
                try {
                    data.put("type",set.getString("type"));
                    data.put("questionId",set.getInt("questionId"));
                    data.put("purchStar",set.getString("purch_star"));
                    data.put("date",set.getLong("date"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return data;

            }
        });
    }







}
