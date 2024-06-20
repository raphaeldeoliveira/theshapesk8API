package com.theshapesk8.theshapesk8API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theshapesk8.theshapesk8API.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
