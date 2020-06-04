package com.zerotouch.JpaRepositories.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerotouch.EntityScan.bean.UnregisteredUser;

public interface UnregisterUserRepositiory extends JpaRepository<UnregisteredUser, Integer> {

	public List<UnregisteredUser> findByMobileNo(long mobileNo);
	
}
