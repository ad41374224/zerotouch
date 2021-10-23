package com.zerotouch.JpaRepositories.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerotouch.EntityScan.bean.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query(value="SELECT * FROM Transaction U WHERE U.user_id = ?1 AND U.merchant_id = ?2", nativeQuery = true)
	public List<Transaction> findByUserIdAndMerchantId(int userId, int merchantId);
	
	@Query(value="SELECT * FROM Transaction U where U.user_id = ?1 OR U.merchant_id = ?1", nativeQuery = true)
	public List<Transaction> findUserTransactions(int userId, Pageable pageable);
}
