package com.smallworld.dal.repository;

import com.smallworld.data.Transaction;

import java.util.List;

public interface TransactionsRepository {

    List<Transaction> findAll();
}
