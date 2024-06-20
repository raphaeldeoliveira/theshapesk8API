package com.theshapesk8.theshapesk8API.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.exceptions.ResourceNotFoundException;
import com.theshapesk8.theshapesk8API.model.ImagemProduct;
import com.theshapesk8.theshapesk8API.model.Order;
import com.theshapesk8.theshapesk8API.repositories.OrderRepository;

@Service
public class OrderServices {
	
	private Logger logger = Logger.getLogger(ClientServices.class.getName());

	@Autowired
	OrderRepository repository;
	
	public List<Order> findAll() {
		logger.info("Finding all orders");

		return repository.findAll();
	}
	
	public Order findById(Long id) {
		logger.info("Finding one image of product!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public Order create(Order order) {
		logger.info("Creating one image of product!");
		return repository.save(order);
	}
	
	public Order update(Order order) {
		logger.info("Updating one image of product!");
		
		// primeiro recuperamos o person depois atualizamos os dados e salvamos
		// assim não sobrescrevemos o objeto
		var entity = repository.findById(order.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setValorTotal(order.getValorTotal());
		entity.setData(order.getData());
		entity.setCliente(order.getCliente());
		entity.setProduto(order.getProduto());
		
		return repository.save(order);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one product product of image!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
}
