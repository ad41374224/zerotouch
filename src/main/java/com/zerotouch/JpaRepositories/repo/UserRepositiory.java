package com.zerotouch.JpaRepositories.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerotouch.EntityScan.bean.UnregisteredUser;
import com.zerotouch.EntityScan.bean.User;

public interface UserRepositiory extends JpaRepository<User, Integer> {

	public List<User> findByMobileNo(long mobileNo);
}
