package com.qcloud.weapp.demo.entity;

public class PurchExamRecord {
  private Long id;
  private String openid;
  private Long purch_star;
  private Long remain_times;
  private Long transaction;
  private Long delete_flag;
  private String create_date;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public Long getPurch_star() {
    return purch_star;
  }

  public void setPurch_star(Long purch_star) {
    this.purch_star = purch_star;
  }

  public Long getRemain_times() {
    return remain_times;
  }

  public void setRemain_times(Long remain_times) {
    this.remain_times = remain_times;
  }

  public Long getTransaction() {
    return transaction;
  }

  public void setTransaction(Long transaction) {
    this.transaction = transaction;
  }

  public Long getDelete_flag() {
    return delete_flag;
  }

  public void setDelete_flag(Long delete_flag) {
    this.delete_flag = delete_flag;
  }

  public String getCreate_date() {
    return create_date;
  }

  public void setCreate_date(String create_date) {
    this.create_date = create_date;
  }
}
