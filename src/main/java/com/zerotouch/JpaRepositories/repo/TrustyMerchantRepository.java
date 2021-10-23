package com.zerotouch.JpaRepositories.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerotouch.EntityScan.bean.TrustyCustomer;

public interface TrustyMerchantRepository extends JpaRepository<TrustyCustomer, Integer> {

	@Query(value="SELECT * FROM Trusty_Customer U WHERE U.cust_id = ?1 AND U.mcht_id = ?2", nativeQuery = true)
	public List<TrustyCustomer> findByUserIdAndMerchantId(int userId, int merchantId);
}
