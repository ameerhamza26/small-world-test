package com.smallworld;

import com.smallworld.dal.repository.TransactionRepositoryImpl;
import com.smallworld.dal.repository.TransactionsRepository;
import com.smallworld.data.Transaction;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher(new TransactionRepositoryImpl());
        System.out.println("getTotalTransactionAmount is = " + transactionDataFetcher.getTotalTransactionAmount());
        System.out.println("getTotalTransactionAmountSentBy is = " + transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby"));
        System.out.println("getMaxTransactionAmount is = " + transactionDataFetcher.getMaxTransactionAmount());
        System.out.println("countUniqueClients is = " + transactionDataFetcher.countUniqueClients());
        System.out.println("hasOpenComplianceIssues is = " + transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
        System.out.println("getTransactionsByBeneficiaryName is = " + transactionDataFetcher.getTransactionsByBeneficiaryName());
        System.out.println("getUnsolvedIssueIds is = " + transactionDataFetcher.getUnsolvedIssueIds());
        System.out.println("getAllSolvedIssueMessages is = " + transactionDataFetcher.getAllSolvedIssueMessages());
        System.out.println("getTop3TransactionsByAmount is = " + transactionDataFetcher.getTop3TransactionsByAmount());
        System.out.println("getTopSender is = " + transactionDataFetcher.getTopSender());
    }

    private TransactionsRepository transactionsRepository;

    TransactionDataFetcher(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().mapToDouble(t -> t.getAmount()).sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().filter(t -> senderFullName.equals(t.getSenderFullName())).mapToDouble(t -> t.getAmount()).sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().mapToDouble(t -> t.getAmount()).max().getAsDouble();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        Map<String, Boolean> countMap = new HashMap<>();
        transactionList.stream().forEach(t -> {
            countMap.computeIfAbsent(t.getSenderFullName(), s -> true);
            countMap.computeIfAbsent(t.getBeneficiaryFullName(), s -> true);
        });

        return countMap.keySet().stream().count();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {

        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream()
                .anyMatch(t -> (clientFullName.equals(t.getSenderFullName()) ||
                        clientFullName.equals(t.getBeneficiaryFullName())) &&
                        !t.isIssueSolved());
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().filter(t -> !t.isIssueSolved()).map(Transaction::getIssueId).collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().filter(t -> t.isIssueSolved()).map(Transaction::getIssueMessage).collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        return transactionList.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).limit(3).toList();
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public String getTopSender() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        Map<String, Double> countMap = new HashMap<>();
        transactionList.stream().forEach(t -> {
            countMap.computeIfAbsent(t.getSenderFullName(), k -> 0.0);
        });

        transactionList.stream().forEach(t -> {
            countMap.compute(t.getSenderFullName(), (k, v) -> v + t.getAmount());
        });

        double max = Collections.max(countMap.values());

        return countMap.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .findFirst().get().toString();
    }

}
