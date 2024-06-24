package com.theshapesk8.theshapesk8API.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theshapesk8.theshapesk8API.model.Order;
import com.theshapesk8.theshapesk8API.service.OrderServices;

@RestController
@RequestMapping("/client")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class OrderController {
	
	@Autowired
	private OrderServices orderService;
	
	/*@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Order> findAll(@PathVariable(value = "id") Long id) {
		
	}*/

}
