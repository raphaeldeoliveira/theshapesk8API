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

import com.theshapesk8.theshapesk8API.model.Product;
import com.theshapesk8.theshapesk8API.service.ProductServices;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductServices service;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Product> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Product findById(@PathVariable(value = "id") Long id) throws Exception {
		return service.findById(id);
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Product create(@RequestBody Product product) {
		return service.create(product);
	}
	
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Product update(@RequestBody Product product) {
		return service.update(product);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
