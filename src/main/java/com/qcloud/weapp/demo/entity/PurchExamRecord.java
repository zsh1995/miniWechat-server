package com.qcloud.weapp.demo.entity;

public class PurchExamRecord {
  private Long id;
  private String openId;
  private Long purchStar;
  private Long remainTimes;
  private Long transaction;
  private Long deleteFlag;
  private String createDate;
  private String tradeNo;

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Long getPurchStar() {
    return purchStar;
  }

  public void setPurchStar(Long purchStar) {
    this.purchStar = purchStar;
  }

  public Long getRemainTimes() {
    return remainTimes;
  }

  public void setRemainTimes(Long remainTimes) {
    this.remainTimes = remainTimes;
  }

  public Long getTransaction() {
    return transaction;
  }

  public void setTransaction(Long transaction) {
    this.transaction = transaction;
  }

  public Long getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(Long deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
}
