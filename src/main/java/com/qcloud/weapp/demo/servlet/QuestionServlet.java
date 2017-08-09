package com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.demo.dto.OptionsDTO;
import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.service.PracticeService;
import com.qcloud.weapp.demo.service.PracticeServiceImpl;
import com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

    private PracticeService practiceService = new PracticeServiceImpl();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        JSONObject result = new JSONObject();

        try {
            UserInfo userInfo = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);
            int stars = jsonObject.getInt("stars");
            int groupId = jsonObject.getInt("groupId");
            List<QuestionDTO> list = initDatas(practiceService.getDatas(userInfo.getOpenId(),stars,groupId));
            JSONObject data = new JSONObject();
            JSONArray listArray = JSONArray.fromObject(list);
            data.put("questionlist",listArray);
            result.put("code", 0);
            result.put("message", "OK");
            result.put("data", data);
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result.toString());

    }
}
