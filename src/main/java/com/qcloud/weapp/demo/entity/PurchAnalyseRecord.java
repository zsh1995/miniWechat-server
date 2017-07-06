package com.qcloud.weapp.demo.entity;

public class PurchAnalyseRecord {
  private Long id;
  private String openId;
  private Long purchStar;
  private Long purchGroup;
  private Long transaction;
  private Long deleteFlag;
  private String createTime;
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

  public Long getPurchGroup() {
    return purchGroup;
  }

  public void setPurchGroup(Long purchGroup) {
    this.purchGroup = purchGroup;
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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
