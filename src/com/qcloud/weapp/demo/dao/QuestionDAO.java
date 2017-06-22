package com.qcloud.weapp.demo.dao;

import com.qcloud.weapp.demo.dto.QuestionDTO;
import com.qcloud.weapp.demo.mapper.QuestionMapper;
import com.qcloud.weapp.demo.util.ObjectMapper;
import com.qcloud.weapp.demo.util.OptTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<QuestionDTO> getQuestionWork(int num){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT * FROM wechat_questions_work WHERE id in (?,?,?,?,?,?)";
        Integer[] args = {num,num+1,num+2,num+3,num+4,num+5};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getQuestionLove(int num){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT * FROM wechat_questions_love WHERE id in (?,?,?,?,?,?)";
        Integer[] args = {num,num+1,num+2,num+3,num+4,num+5};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }

    public List<QuestionDTO> getQuestionSocial(int num){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT * FROM wechat_questions_social WHERE id in (?,?,?,?,?,?)";
        Integer[] args = {num,num+1,num+2,num+3,num+4,num+5};
        return (List<QuestionDTO>)optTemplate.query(sql,args,new QuestionMapper());
    }


    public Integer getAllCount(){
        OptTemplate optTemplate = new OptTemplate();
        String sql = "SELECT MAX(id) FROM wechat_questions_work";
        Integer[] args = {};
        int count = (int)optTemplate.find(sql, args, new ObjectMapper() {
            @Override
            public Object mapping(ResultSet set) {
                int id = 0;
                try {
                    id = set.getInt("id");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return id;
            }
        });
        return count;
    }

}
