package com.fabricgroup.test.processor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.fabricgroup.test.data.Balance;
import com.fabricgroup.test.data.Loan;
import com.fabricgroup.test.data.Payment;

/**
 * LoanProcessor class read, parse input file, calculate loan payment and print balance to console
 * 
 * @author ivanlunardi
 *
 */
@Component
public class LoanProcessor implements Processor {

  private static final Logger log = LoggerFactory.getLogger(LoanProcessor.class);

  /** 
   * Rean input file
   * 
   * (non-Javadoc)
   * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
   */
  @Override
  public void process(Exchange exchange) throws Exception {
    String fileName = (String) exchange.getIn().getHeader("CamelFileName");
    log.debug("Parsing input file: " + fileName);

    InputStream is = exchange.getIn().getBody(InputStream.class);
    List<Object> objList = parseFile(is);

    if(!CollectionUtils.isEmpty(objList)) {
      List<String> balances = calculateBalance(getLoanMap(objList), getPaymentMap(objList), getBalanceList(objList));

      printBalances(balances);
    }
  }

  /**
   * Parse file and put in array list of object
   * 
   * @param is
   * @return
   */
  public List<Object> parseFile(InputStream is) {
    List<Object> objList = new ArrayList<Object>();

    BeanReader reader = null;
    try {
      StreamFactory factory = StreamFactory.newInstance();
      factory.loadResource("camel/beanio-mapping.xml");

      reader = factory.createReader("inputFileStream", new InputStreamReader(is));

      Object obj;
      while((obj = reader.read()) != null) {
        objList.add(obj);
      }
    } catch(Exception ex) {
      log.error(ex.getMessage());
    } finally {
      reader.close();
    }

    return objList;
  }

  /**
   * Convert list of object to loan hashmap table
   * 
   * @param objList
   * @return
   */
  public Map<String, Loan> getLoanMap(List<Object> objList) {
    return objList.stream()
                  .filter(Loan.class::isInstance)
                  .map(Loan.class::cast)
                  .collect(Collectors.toMap(Loan::getKey, Function.identity()));
  }

  /**
   * Convert list of object to payment hashmap table
   * 
   * @param objList
   * @return
   */
  public Map<String, Payment> getPaymentMap(List<Object> objList) {
    return objList.stream()
                  .filter(Payment.class::isInstance)
                  .map(Payment.class::cast)
                  .collect(Collectors.toMap(Payment::getKey, Function.identity()));
  }

  /**
   * Convert list of object to list of balance
   * 
   * @param objList
   * @return
   */
  public List<Balance> getBalanceList(List<Object> objList) {
    return objList.stream()
                  .filter(Balance.class::isInstance)
                  .map(Balance.class::cast)
                  .collect(Collectors.toList());
  }

  /**
   * Calculate loan payment and return list of print out balance
   * 
   * @param balances
   * @param loanMap
   * @param paymentMap
   */
  public List<String> calculateBalance(Map<String, Loan> loanMap, Map<String, Payment> paymentMap, List<Balance> balanceList) {
    List<String> balances = new ArrayList<String>();
    for(Balance balance : balanceList) {
      Loan loan = loanMap.get(balance.getKey());

      Integer emi = new BigDecimal(loan.getMonthlyPaymentAmount() * balance.getEmiNo()).intValue();
      Integer remaining = loan.getTerms() * 12 - balance.getEmiNo();

      Payment payment = paymentMap.get(balance.getKey());
      if(payment != null && payment.getEmiNo() <= balance.getEmiNo()) {
        emi = emi + payment.getLumpSumAmount();
        remaining = remaining - (new BigDecimal(payment.getLumpSumAmount()).divide(new BigDecimal(loan.getMonthlyPaymentAmount()), BigDecimal.ROUND_UP)).intValue() + 1;
      }

      balances.add(String.format("%s %s %s %s", loan.getBankName(), loan.getBorrower(), emi, remaining));
    }
    return balances;
  }

  /**
   * Print balances to console
   * 
   * @param balances
   */
  private void printBalances(List<String> balances) {
    balances.stream()
            .forEach(s -> System.out.println(s));
  }

}
