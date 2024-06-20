package com.theshapesk8.theshapesk8API.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.exceptions.ResourceNotFoundException;
import com.theshapesk8.theshapesk8API.model.Product;
import com.theshapesk8.theshapesk8API.model.ProductDetail;
import com.theshapesk8.theshapesk8API.repositories.ProductRepository;

@Service
public class ProductServices {

	private Logger logger = Logger.getLogger(ClientServices.class.getName());
	
	@Autowired
	ProductRepository repository;
	
	public List<Product> findAll() {
		logger.info("Finding all Products");

		return repository.findAll();
	}
	
	public Product findById(Long id) {
		logger.info("Finding one Product!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public Product create(Product product) {
		logger.info("Creating one Product!");
		return repository.save(product);
	}
	
	public Product update(Product product) {
		logger.info("Updating one product!");
		
		// primeiro recuperamos o person depois atualizamos os dados e salvamos
		// assim não sobrescrevemos o objeto
		var entity = repository.findById(product.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setTamanho(product.getTamanho());
		entity.setQuantidade(product.getQuantidade());
		entity.setProductDetail(product.getProductDetail());
		
		return repository.save(product);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one product!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
}
