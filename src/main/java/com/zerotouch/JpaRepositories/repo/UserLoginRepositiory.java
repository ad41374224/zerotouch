package com.zerotouch.JpaRepositories.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerotouch.EntityScan.bean.UnregisteredUser;
import com.zerotouch.EntityScan.bean.UserLogin;

public interface UserLoginRepositiory extends JpaRepository<UserLogin, Integer> {

	
}
