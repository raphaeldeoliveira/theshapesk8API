package com.theshapesk8.theshapesk8API.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theshapesk8.theshapesk8API.model.ProductDetail;
import com.theshapesk8.theshapesk8API.service.ProductDetailServices;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {
	
	@Autowired
	private ProductDetailServices service;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDetail> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ProductDetail findById(@PathVariable(value = "id") Long id) throws Exception {
		return service.findById(id);
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ProductDetail create(@RequestBody ProductDetail productDetail) {
		return service.create(productDetail);
	}
	
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ProductDetail update(@RequestBody ProductDetail productDetail) {
		return service.update(productDetail);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
