package com.theshapesk8.theshapesk8API.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.exceptions.ResourceNotFoundException;
import com.theshapesk8.theshapesk8API.model.Address;
import com.theshapesk8.theshapesk8API.model.ProductDetail;
import com.theshapesk8.theshapesk8API.repositories.ProductDetailRepository;

@Service
public class ProductDetailServices {
	
	private Logger logger = Logger.getLogger(ClientServices.class.getName());
	
	@Autowired
	ProductDetailRepository repository;
	
	public List<ProductDetail> findAll() {
		logger.info("Finding all ProductDetails");

		return repository.findAll();
	}
	
	public ProductDetail findById(Long id) {
		logger.info("Finding one ProductDetail!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public ProductDetail create(ProductDetail productDetail) {
		logger.info("Creating one ProductDetail!");
		return repository.save(productDetail);
	}
	
	public ProductDetail update(ProductDetail productDetail) {
		logger.info("Updating one ProductDetail!");
		
		// primeiro recuperamos o person depois atualizamos os dados e salvamos
		// assim não sobrescrevemos o objeto
		var entity = repository.findById(productDetail.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setNome(productDetail.getNome());
		entity.setDescricao(productDetail.getDescricao());
		entity.setValor(productDetail.getValor());
		entity.setCategoria(productDetail.getCategoria());
		//entity.setSubCategoria(productDetail.getSubCategoria());
		entity.setSubcategoria(productDetail.getSubcategoria());
		entity.setMarca(productDetail.getMarca());
		
		return repository.save(productDetail);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one product Detail!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}

}
