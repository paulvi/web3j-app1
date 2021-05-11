package org.web3j.generated.contracts;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.EVMTest;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;

//@Slf4j
@EVMTest
class DepositContractTest {
  private static final Logger log = LoggerFactory.getLogger(DepositContractTest.class);

  private static DepositContract depositContract;

  private static BigInteger ONE = BigInteger.ONE;
  private static BigInteger TWO = BigInteger.TWO;

  @BeforeAll
  static void deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) throws Exception {
    log.info("deploy()");
    depositContract = DepositContract.deploy(web3j, transactionManager, contractGasProvider, ONE).send();
  }

  @Test
  public void viewInterestRate() throws Exception {
    log.info("viewInterestRate()");
    BigInteger interestRate = depositContract.viewInterestRate().send();
    log.info("viewInterestRate( interestRate={}", interestRate);
    Assertions.assertEquals(ONE, interestRate);
  }

  //@Ignore
  //@Disabled
  @Test
  public void changeInterestRate() throws Exception {
    log.info("changeInterestRate()");
    TransactionReceipt transactionReceiptVar = depositContract.changeInterestRate(TWO).send();
    Assertions.assertTrue(transactionReceiptVar.isStatusOK());
    //testNewGreeting()
    //Thread.sleep(1000);
    //BigInteger bn = transactionReceiptVar.getBlockNumber();
    log.info(transactionReceiptVar.toString());
    BigInteger interestRate = depositContract.viewInterestRate().send();
    log.info("changeInterestRate( interestRate={}", interestRate);
    Assertions.assertEquals(TWO, interestRate);
  }


}
