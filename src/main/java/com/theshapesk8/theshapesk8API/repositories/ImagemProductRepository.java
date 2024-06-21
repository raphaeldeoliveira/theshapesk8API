package com.theshapesk8.theshapesk8API.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theshapesk8.theshapesk8API.model.ImagemProduct;

public interface ImagemProductRepository extends JpaRepository<ImagemProduct, Long> {

	List<ImagemProduct> findByProductDetailId(Long productDetailId);
	
}
