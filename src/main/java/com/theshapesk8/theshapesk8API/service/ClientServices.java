package com.theshapesk8.theshapesk8API.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.exceptions.ResourceNotFoundException;
import com.theshapesk8.theshapesk8API.model.Client;
import com.theshapesk8.theshapesk8API.repositories.ClientRepository;

@Service
public class ClientServices {

	private Logger logger = Logger.getLogger(ClientServices.class.getName());
	
	@Autowired
	ClientRepository repository;
	
	public List<Client> findAll() {
		logger.info("Finding all clients");

		return repository.findAll();
	}
	
	public Client findById(Long id) {
		logger.info("Finding one client!");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public Client create(Client client) {
		logger.info("Creating one client!");
		return repository.save(client);
	}
	
	public Client update(Client client) {
		logger.info("Updating one client!");
		
		// primeiro recuperamos o person depois atualizamos os dados e salvamos
		// assim não sobrescrevemos o objeto
		var entity = repository.findById(client.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setNome(client.getNome());
		entity.setCpf(client.getCpf());
		entity.setEmail(client.getEmail());
		entity.setTelefone(client.getTelefone());
		entity.setSenha(client.getSenha());
		entity.setDataNascimento(client.getDataNascimento());
		
		return repository.save(client);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one client!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
}
