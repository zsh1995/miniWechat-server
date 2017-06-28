package main.java.com.qcloud.weapp.demo.servlet;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import main.java.com.qcloud.weapp.demo.dto.PracticeRecordDTO;
import main.java.com.qcloud.weapp.demo.service.PracticeService;
import main.java.com.qcloud.weapp.demo.service.PracticeServiceImpl;
import main.java.com.qcloud.weapp.demo.util.JsonReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */
@WebServlet("/getScores")
public class GetScoresServlet extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService service = new LoginService(request, response);
        PracticeService practiceService = new PracticeServiceImpl();
        try {
            UserInfo userInfo = service.check();
            JSONObject jsonObject = JsonReader.receivePost(request);
            int stars = jsonObject.getInt("stars");
            List<PracticeRecordDTO> practiceRecords = practiceService.getRecord(userInfo.getOpenId(),stars);
            JSONObject result = new JSONObject();
            JSONObject data = new JSONObject();
            JSONArray listArray = JSONArray.fromObject(practiceRecords);
            data.put("practiceRecords",listArray);
            result.put("code", 0);
            result.put("message", "OK");
            result.put("data", data);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result.toString());
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }


    }


    }
