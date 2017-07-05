package com.qcloud.weapp.demo.entity;

public class PurchAnalyseRecord {
  private Long id;
  private String openid;
  private Long purch_star;
  private Long purch_group;
  private Long transaction;
  private Long delete_flag;
  private String create_time;

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

  public Long getPurch_group() {
    return purch_group;
  }

  public void setPurch_group(Long purch_group) {
    this.purch_group = purch_group;
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

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }
}
