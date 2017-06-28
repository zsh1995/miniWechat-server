package main.java.com.qcloud.weapp.demo.dto;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
public class QuestionDTO {
    private String content;

    private List<OptionsDTO> options;

    private String type;

    private String tips;

    private String analyse;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

}
