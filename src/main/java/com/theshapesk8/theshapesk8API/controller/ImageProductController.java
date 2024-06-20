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

import com.theshapesk8.theshapesk8API.model.ImagemProduct;
import com.theshapesk8.theshapesk8API.service.ImagemProductServices;

@RestController
@RequestMapping("/imageProduct")
public class ImageProductController {
	
	@Autowired
	private ImagemProductServices service;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ImagemProduct> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ImagemProduct findById(@PathVariable(value = "id") Long id) throws Exception {
		return service.findById(id);
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ImagemProduct create(@RequestBody ImagemProduct imagemProduct) {
		return service.create(imagemProduct);
	}
	
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ImagemProduct update(@RequestBody ImagemProduct imagemProduct) {
		return service.update(imagemProduct);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
