package com.zerotouch.JpaRepositories.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerotouch.EntityScan.bean.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

}
