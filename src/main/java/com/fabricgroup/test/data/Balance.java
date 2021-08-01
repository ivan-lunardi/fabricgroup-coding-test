package com.fabricgroup.test.data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Balance {

  private String recordType;

  private String bankName;

  private String borrower;

  private Integer emiNo;

  public String getRecordType() {
    return recordType;
  }

  public void setRecordType(String recordType) {
    this.recordType = recordType;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBorrower() {
    return borrower;
  }

  public void setBorrower(String borrower) {
    this.borrower = borrower;
  }

  public Integer getEmiNo() {
    return emiNo;
  }

  public void setEmiNo(Integer emiNo) {
    this.emiNo = emiNo;
  }

  public String getKey() {
    return bankName + "_" + borrower;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append(recordType)
        .append(" "+bankName)
        .append(" "+borrower)
        .append(" "+emiNo)
        .toString();
  }

}
