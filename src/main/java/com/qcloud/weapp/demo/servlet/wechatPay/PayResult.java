package com.qcloud.weapp.demo.servlet.wechatPay;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qcloud.weapp.demo.common.MapperTools;
import com.qcloud.weapp.demo.common.StreamUtil;
import com.qcloud.weapp.demo.dao.WechatPayDAO;
import com.qcloud.weapp.demo.dto.OrderReturnDTO;
import com.qcloud.weapp.demo.dto.weixinPay.WechatResult;
import com.qcloud.weapp.demo.dto.weixinPay.WechatResultDTO;
import com.qcloud.weapp.demo.entity.PurchExamRecord;
import com.qcloud.weapp.demo.entity.WeixinReturnStatements;
import com.qcloud.weapp.demo.service.uerRight.UserRightService;
import com.qcloud.weapp.demo.service.uerRight.UserRightServiceImpl;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;


/**
 * 接收支付结果
 */
@WebServlet("/weixinpay/PayResult")
public class PayResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger L = Logger.getLogger(PayResult.class);

	WechatPayDAO payDAO = new WechatPayDAO();

	UserRightService userRightService = new UserRightServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String reqParams = StreamUtil.read(request.getInputStream());
		L.info("-------支付结果:"+reqParams);
		XStream xStream = new XStream();
		xStream.alias("xml", WechatResultDTO.class);
		WechatResultDTO returnInfo = (WechatResultDTO)xStream.fromXML(reqParams);
		//写库
		WeixinReturnStatements statements = MapperTools.entityToDTO(WeixinReturnStatements.class,returnInfo);
		L.info("mapper result:"+statements.getAppid()+","+statements.getBankType());
		payDAO.insertNewPayReturn(statements);
		PurchExamRecord purchExamRecord = new PurchExamRecord();
		purchExamRecord.setTransaction(1);
		purchExamRecord.setRemainTimes(6);
		purchExamRecord.setTradeNo(returnInfo.getOut_trade_no());
		payDAO.setExamPayRecordStatus(purchExamRecord);
		userRightService.inserUserRight(purchExamRecord.getOpenId(),purchExamRecord.getTradeNo());
		StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
		response.getWriter().append(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
