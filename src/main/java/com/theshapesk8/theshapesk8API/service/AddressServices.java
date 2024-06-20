package com.theshapesk8.theshapesk8API.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.exceptions.ResourceNotFoundException;
import com.theshapesk8.theshapesk8API.model.Address;
import com.theshapesk8.theshapesk8API.repositories.AddressRepository;

@Service
public class AddressServices {
	
	private Logger logger = Logger.getLogger(ClientServices.class.getName());
	
	@Autowired
	AddressRepository repository;
	
	public List<Address> findAll() {
		logger.info("Finding all address");

		return repository.findAll();
	}
	
	public Address findById(Long id) {
		logger.info("Finding one address!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public Address create(Address address) {
		logger.info("Creating one address!");
		return repository.save(address);
	}
	
	public Address update(Address address) {
		logger.info("Updating one address!");
		
		// primeiro recuperamos o person depois atualizamos os dados e salvamos
		// assim não sobrescrevemos o objeto
		var entity = repository.findById(address.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setCep(address.getCep());
		entity.setRua(address.getRua());
		entity.setBairro(address.getBairro());
		entity.setCidade(address.getCidade());
		entity.setUf(address.getUf());
		entity.setComplemento(address.getComplemento());
		entity.setNumeroCasa(address.getNumeroCasa());
		
		return repository.save(address);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one address!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}

}
