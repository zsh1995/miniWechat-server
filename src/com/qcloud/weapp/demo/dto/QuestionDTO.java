package com.qcloud.weapp.demo.dto;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
public class QuestionDTO {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<OptionsDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsDTO> options) {
        this.options = options;
    }

    private List<OptionsDTO> options;

}
