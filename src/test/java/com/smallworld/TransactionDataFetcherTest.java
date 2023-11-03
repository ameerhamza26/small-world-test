package com.smallworld;

import com.smallworld.dal.repository.TransactionRepositoryImpl;
import com.smallworld.data.Transaction;
import com.smallworld.exceptions.InvalidFilePathException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TransactionDataFetcherTest {

    /*
    getTotalTransactionAmount is = 4371.37
    getTotalTransactionAmountSentBy is = 828.26
    getMaxTransactionAmount is = 985.0
    countUniqueClients is = 14
    hasOpenComplianceIssues is = true
    getTransactionsByBeneficiaryName is = {Luca Changretta=[Transaction(mtn=45431585, amount=89.77, senderFullName=Billy Kimber, senderAge=58, beneficiaryFullName=Luca Changretta, beneficiaryAge=46, issueId=0, issueSolved=true, issueMessage=null)], Major Campbell=[Transaction(mtn=645645111, amount=215.17, senderFullName=Billy Kimber, senderAge=58, beneficiaryFullName=Major Campbell, beneficiaryAge=41, issueId=0, issueSolved=true, issueMessage=null)], MacTavern=[Transaction(mtn=6516461, amount=33.22, senderFullName=Aunt Polly, senderAge=34, beneficiaryFullName=MacTavern, beneficiaryAge=30, issueId=0, issueSolved=true, issueMessage=null)], Ben Younger=[Transaction(mtn=5465465, amount=985.0, senderFullName=Arthur Shelby, senderAge=60, beneficiaryFullName=Ben Younger, beneficiaryAge=47, issueId=15, issueSolved=false, issueMessage=Something's fishy)], Winston Churchill=[Transaction(mtn=36448252, amount=154.15, senderFullName=Billy Kimber, senderAge=58, beneficiaryFullName=Winston Churchill, beneficiaryAge=48, issueId=0, issueSolved=true, issueMessage=null)], Aberama Gold=[Transaction(mtn=96132456, amount=67.8, senderFullName=Aunt Polly, senderAge=34, beneficiaryFullName=Aberama Gold, beneficiaryAge=58, issueId=0, issueSolved=true, issueMessage=null)], Arthur Shelby=[Transaction(mtn=1284564, amount=150.2, senderFullName=Tom Shelby, senderAge=22, beneficiaryFullName=Arthur Shelby, beneficiaryAge=60, issueId=2, issueSolved=true, issueMessage=Never gonna give you up), Transaction(mtn=1284564, amount=150.2, senderFullName=Tom Shelby, senderAge=22, beneficiaryFullName=Arthur Shelby, beneficiaryAge=60, issueId=3, issueSolved=false, issueMessage=Looks like money laundering)], Michael Gray=[Transaction(mtn=32612651, amount=666.0, senderFullName=Grace Burgess, senderAge=31, beneficiaryFullName=Michael Gray, beneficiaryAge=58, issueId=54, issueSolved=false, issueMessage=Something ain't right), Transaction(mtn=32612651, amount=666.0, senderFullName=Grace Burgess, senderAge=31, beneficiaryFullName=Michael Gray, beneficiaryAge=58, issueId=78, issueSolved=true, issueMessage=Never gonna run around and desert you), Transaction(mtn=32612651, amount=666.0, senderFullName=Grace Burgess, senderAge=31, beneficiaryFullName=Michael Gray, beneficiaryAge=58, issueId=99, issueSolved=false, issueMessage=Don't let this transaction happen)], Oswald Mosley=[Transaction(mtn=1651665, amount=97.66, senderFullName=Tom Shelby, senderAge=22, beneficiaryFullName=Oswald Mosley, beneficiaryAge=37, issueId=65, issueSolved=true, issueMessage=Never gonna let you down)], Alfie Solomons=[Transaction(mtn=663458, amount=430.2, senderFullName=Tom Shelby, senderAge=22, beneficiaryFullName=Alfie Solomons, beneficiaryAge=33, issueId=1, issueSolved=false, issueMessage=Looks like money laundering)]}
    getUnsolvedIssueIds is = [1, 3, 99, 54, 15]
    getAllSolvedIssueMessages is = [Never gonna give you up, null, Never gonna let you down, null, Never gonna run around and desert you, null, null, null]
    getTop3TransactionsByAmount is = [Transaction(mtn=5465465, amount=985.0, senderFullName=Arthur Shelby, senderAge=60, beneficiaryFullName=Ben Younger, beneficiaryAge=47, issueId=15, issueSolved=false, issueMessage=Something's fishy), Transaction(mtn=32612651, amount=666.0, senderFullName=Grace Burgess, senderAge=31, beneficiaryFullName=Michael Gray, beneficiaryAge=58, issueId=54, issueSolved=false, issueMessage=Something ain't right), Transaction(mtn=32612651, amount=666.0, senderFullName=Grace Burgess, senderAge=31, beneficiaryFullName=Michael Gray, beneficiaryAge=58, issueId=78, issueSolved=true, issueMessage=Never gonna run around and desert you)]
    getTopSender is = Grace Burgess=1998.0
     */

    TransactionDataFetcher transactionDataFetcher;
    TransactionRepositoryImpl transactionsRepository;

    @BeforeEach
    void init() {
        //Initialize the object here
        System.out.println("Initializing before test");
        transactionsRepository = new TransactionRepositoryImpl();
        transactionDataFetcher = new TransactionDataFetcher(transactionsRepository);
    }

    @Test
    public void test_InvalidFilePathException() {
        Assertions.assertThrows(InvalidFilePathException.class, () -> {
            transactionsRepository.findAllByPropertyFile("abc");
        });
    }

    @Test
    public void test_getTotalTransactionAmount_success() {
        Assertions.assertEquals(4371.37, transactionDataFetcher.getTotalTransactionAmount());
    }

    @Test
    public void test_getTotalTransactionAmountSentBy_success() {
        Assertions.assertEquals(828.26, transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby"));
    }

    @Test
    public void test_getMaxTransactionAmount_success() {
        Assertions.assertEquals(985.0, transactionDataFetcher.getMaxTransactionAmount());
    }

    @Test
    public void test_countUniqueClients_success() {
        Assertions.assertEquals(14, transactionDataFetcher.countUniqueClients());
    }

    @Test
    public void test_hasOpenComplianceIssues_success() {
        Assertions.assertTrue(transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
    }

    @Test
    public void test_getTransactionsByBeneficiaryName_success() {
        Map<String, List<Transaction>> transactionByBeneficiaryName = transactionDataFetcher.getTransactionsByBeneficiaryName();
        Assertions.assertEquals(2, transactionByBeneficiaryName.get("Arthur Shelby").size());
        Assertions.assertEquals(1, transactionByBeneficiaryName.get("Alfie Solomons").size());
        Assertions.assertEquals(1, transactionByBeneficiaryName.get("Aberama Gold").size());
        Assertions.assertEquals(1, transactionByBeneficiaryName.get("Ben Younger").size());
    }

    @Test
    public void test_getUnsolvedIssueIds_success() {
        Assertions.assertEquals(new HashSet<>(Arrays.asList(1, 3, 99, 54, 15)), transactionDataFetcher.getUnsolvedIssueIds());
    }

    @Test
    public void test_getAllSolvedIssueMessages_success() {
        Assertions.assertTrue(transactionDataFetcher.getAllSolvedIssueMessages().contains("Never gonna let you down"));
    }

    @Test
    public void test_getAllSolvedIssueMessages_false() {
        Assertions.assertFalse(transactionDataFetcher.getAllSolvedIssueMessages().contains("Looks like money laundering"));
    }

    @Test
    public void test_getTop3TransactionsByAmount_success() {
        List<Transaction> transactions = transactionDataFetcher.getTop3TransactionsByAmount();
        List<Double> amounts = new ArrayList<>();
        transactions.forEach(t -> {
            amounts.add(t.getAmount());
        });

        Assertions.assertEquals(Arrays.asList(985.0, 666.0, 666.0), amounts);
    }

    @Test
    public void test_getTopSender_success() {
        Assertions.assertEquals("Grace Burgess", transactionDataFetcher.getTopSender().split("=")[0]);
    }
}
