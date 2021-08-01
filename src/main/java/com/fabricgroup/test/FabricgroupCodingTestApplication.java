package com.fabricgroup.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations={"classpath:camel/fabricgroup-*.xml"})
@SpringBootApplication
public class FabricgroupCodingTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(FabricgroupCodingTestApplication.class, args);
  }

}
