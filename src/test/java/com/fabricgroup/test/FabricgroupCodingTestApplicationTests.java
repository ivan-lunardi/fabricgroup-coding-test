package com.fabricgroup.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.fabricgroup.test.processor.LoanProcessor;

@SpringBootTest
class FabricgroupCodingTestApplicationTests {

  @Value("${input.file.path}")
  private String filePath;

  private LoanProcessor processor;

  @BeforeEach
  private void setup() {
    processor = new LoanProcessor();
  }

  @Test
  void testParseInvalidFile() throws FileNotFoundException {
    InputStream is = new FileInputStream(filePath + "test/invalid_input.txt");
    List<Object> objList = processor.parseFile(is);

    assertEquals(0, objList.size());
  }

  @Test
  void testParseFile() throws FileNotFoundException {
    InputStream is = new FileInputStream(filePath + "test/input.txt");
    List<Object> objList = processor.parseFile(is);

    assertEquals(10, objList.size());
    assertEquals(3, processor.getLoanMap(objList).size());
    assertEquals(3, processor.getPaymentMap(objList).size());
    assertEquals(4, processor.getBalanceList(objList).size());
  }

  @Test
  void testPrintBalance() throws FileNotFoundException {
    InputStream is = new FileInputStream(filePath + "test/input.txt");
    List<Object> objList = processor.parseFile(is);

    List<String> balances = processor.calculateBalance(processor.getLoanMap(objList), processor.getPaymentMap(objList), processor.getBalanceList(objList));

    assertEquals(4, balances.size());
    assertEquals("IDIDI Dale 1326 9", balances.get(0));
    assertEquals("IDIDI Dale 3652 4", balances.get(1));
    assertEquals("UON Shelly 15856 3", balances.get(2));
    assertEquals("MBI Harry 9044 10", balances.get(3));
  }

}
