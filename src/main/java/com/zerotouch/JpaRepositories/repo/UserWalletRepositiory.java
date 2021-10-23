package com.zerotouch.JpaRepositories.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerotouch.EntityScan.bean.UnregisteredUser;
import com.zerotouch.EntityScan.bean.UserWallet;

public interface UserWalletRepositiory extends JpaRepository<UserWallet, Integer> {

}
