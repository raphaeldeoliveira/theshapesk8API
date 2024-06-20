package com.theshapesk8.theshapesk8API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theshapesk8.theshapesk8API.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>  {

}
