package com.fabricgroup.test.data;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Loan {

  private String recordType;

  private String bankName;

  private String borrower;

  private Integer principal;

  private Integer terms;

  private Double interestRate;

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

  public Integer getPrincipal() {
    return principal;
  }

  public void setPrincipal(Integer principal) {
    this.principal = principal;
  }

  public Integer getTerms() {
    return terms;
  }

  public void setTerms(Integer terms) {
    this.terms = terms;
  }

  public Double getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(Double interestRate) {
    this.interestRate = interestRate;
  }

  public Integer getTotalInterestAmount() {
    return new BigDecimal(principal * terms * interestRate).divide(new BigDecimal("100"), BigDecimal.ROUND_UP).intValue();
  }

  public Integer getTotalLoanAmount() {
    return new BigDecimal(principal + getTotalInterestAmount()).intValue();
  }

  public Integer getMonthlyPaymentAmount() {
    return new BigDecimal(getTotalLoanAmount()).divide(new BigDecimal(terms * 12), BigDecimal.ROUND_UP).intValue();
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
        .append(" "+principal)
        .append(" "+terms)
        .append(" "+interestRate)
        .append(" "+getTotalInterestAmount())
        .append(" "+getTotalLoanAmount())
        .toString();
  }

}
