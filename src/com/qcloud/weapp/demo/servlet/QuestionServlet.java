package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dao.QuestionDAO;
import com.qcloud.weapp.demo.dto.OptionsDTO;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
@WebServlet("/getQuestions")
public class QuestionServlet extends HttpServlet{


    private List<QuestionDTO> initDatas(List<QuestionDTO> list){
        for(QuestionDTO dto : list) {
            List<OptionsDTO> options = new ArrayList<>();
            for (int cnt = 1; cnt <= 5; cnt++) {
                OptionsDTO option = new OptionsDTO();
                option.setTip(cnt + "");
                option.setContent("test" + cnt);
                options.add(option);
            }
            dto.setOptions(options);
        }
        return list;

    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        QuestionDAO questionDAO = new QuestionDAO();
        List<QuestionDTO> list = initDatas(questionDAO.getQuestion());
        //UserInfo userInfo = service.check();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        //data.put("userInfo", new JSONObject(userInfo));
        JSONArray listArray = JSONArray.fromObject(list);
        data.put("questionlist",listArray);
        result.put("code", 0);
        result.put("message", "OK");
        result.put("data", data);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result.toString());

    }
}
