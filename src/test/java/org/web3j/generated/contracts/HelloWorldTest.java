package org.web3j.generated.contracts;

import java.lang.Exception;
import java.lang.String;
import java.math.BigInteger;

//import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web3j.EVMTest;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

//@Slf4j
@EVMTest
class HelloWorldTest {
  private static Logger log = LoggerFactory.getLogger(HelloWorldTest.class);

  private static HelloWorld helloWorld;

  @BeforeAll
  static void deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) throws Exception {
    log.info("deploy");
    helloWorld = HelloWorld.deploy(web3j, transactionManager, contractGasProvider, "HELLO").send();
  }

  @Test
  public void greeting() throws Exception {
    log.info("greeting()");
    String stringVar = helloWorld.greeting().send();
    Assertions.assertEquals("HELLO", stringVar);
  }

  //@Ignore
  //@Disabled
  @Test
  public void newGreeting() throws Exception {
    log.info("newGreeting()");
    TransactionReceipt transactionReceiptVar = helloWorld.newGreeting("HELLO2").send();
    Assertions.assertTrue(transactionReceiptVar.isStatusOK());
    //testNewGreeting()
    //Thread.sleep(1000);
    //BigInteger bn = transactionReceiptVar.getBlockNumber();
    log.info(transactionReceiptVar.toString());
    String stringVar = helloWorld.greeting().send();
    Assertions.assertEquals("HELLO2", stringVar);
  }

  @Test
  public void greeting2() throws Exception {
    log.info("greeting2()");
    String stringVar = helloWorld.greeting().send();
    Assertions.assertEquals("HELLO2", stringVar);
  }

}
