package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.dto.QuestionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh1995 on 2017/6/15.
 */
public class QuestionDAO {

    private ResultSet querySQL(String sql) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://47.93.196.237:3306/wechat-BE?useUnicode=true&characterEncoding=utf-8";
        String username ="zsh1995";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url,username,password);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        return statement.executeQuery(sql);

    }

    public List<QuestionDTO> getQuestion(){
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://47.93.196.237:3306/wechat-BE?useUnicode=true&characterEncoding=utf-8";
            String username ="mrzsh";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url,username,password);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM wechat_questions_work";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setContent(resultSet.getString("question_content"));
                questionDTOList.add(questionDTO);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionDTOList;

    }
}
