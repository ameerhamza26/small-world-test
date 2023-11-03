package com.smallworld.dal.repository;

import com.smallworld.data.Transaction;
import com.smallworld.util.FileUtil;
import com.smallworld.util.PropertiesUtil;

import java.util.Arrays;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionsRepository {

    private final static String TRANSACTION_FILE_PATH_PROPERTY = "transaction.file.path";
    private final static String USER_DIR = "user.dir";

    @Override
    public List<Transaction> findAll() {
        return findAllByPropertyFile(System.getProperty(USER_DIR) + PropertiesUtil.getProperty(TRANSACTION_FILE_PATH_PROPERTY));
    }

    public List<Transaction> findAllByPropertyFile(String fileName) {
        String json = FileUtil.readUsingFileInputStream(fileName);
        List<Transaction> transactionList = Arrays.asList(FileUtil.toObjectFromTypedJson(json, Transaction[].class));
        return transactionList;
    }
}
