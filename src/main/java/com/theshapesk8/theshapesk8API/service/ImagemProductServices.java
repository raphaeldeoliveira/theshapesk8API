package com.theshapesk8.theshapesk8API.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.exceptions.ResourceNotFoundException;
import com.theshapesk8.theshapesk8API.model.ImagemProduct;
import com.theshapesk8.theshapesk8API.model.ProductDetail;
import com.theshapesk8.theshapesk8API.repositories.ImagemProductRepository;

@Service
public class ImagemProductServices {

	private Logger logger = Logger.getLogger(ClientServices.class.getName());
	
	@Autowired
	ImagemProductRepository repository;
	
	public List<ImagemProduct> findAll() {
		logger.info("Finding all images of products");

		return repository.findAll();
	}
	
	public ImagemProduct findById(Long id) {
		logger.info("Finding one image of product!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public ImagemProduct create(ImagemProduct imagemProduct) {
		logger.info("Creating one image of product!");
		return repository.save(imagemProduct);
	}
	
	public ImagemProduct update(ImagemProduct imagemProduct) {
		logger.info("Updating one image of product!");
		
		// primeiro recuperamos o person depois atualizamos os dados e salvamos
		// assim não sobrescrevemos o objeto
		var entity = repository.findById(imagemProduct.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setImagem(imagemProduct.getImagem());
		entity.setProductDetail(imagemProduct.getProductDetail());
		
		return repository.save(imagemProduct);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one product product of image!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
	public List<ImagemProduct> findByProductDetailId(Long productDetailId) {
		logger.info("Finding all images for ProductDetail ID: " + productDetailId);
		return repository.findByProductDetailId(productDetailId);
	}
	
}
