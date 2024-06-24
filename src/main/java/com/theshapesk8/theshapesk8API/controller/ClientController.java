package com.theshapesk8.theshapesk8API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theshapesk8.theshapesk8API.model.Client;
import com.theshapesk8.theshapesk8API.model.LoginResponse;
import com.theshapesk8.theshapesk8API.service.ClientServices;

@RestController
@RequestMapping("/client")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class ClientController {
	
	@Autowired
	private ClientServices clientService;
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody Client client) {
	    if (client == null || client.getSenha() == null) {
	        return ResponseEntity.status(400).body("Client or password is missing");
	    }

	    String cpf = client.getCpf();
	    String email = client.getEmail();
	    String senha = client.getSenha();

	    Client validClient = null;

	    if (cpf != null && !cpf.isEmpty()) {
	        validClient = clientService.findByCpfAndSenha(cpf, senha);
	    }

	    if (email != null && !email.isEmpty()) {
	        validClient = clientService.findByEmailAndSenha(email, senha);
	    }

	    if (validClient != null) {
	        Long clientId = validClient.getId();
	        return ResponseEntity.ok(new LoginResponse(clientId, "Login successful"));
	    } else {
	        return ResponseEntity.status(401).body("Invalid credentials");
	    }
	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Client register(@RequestBody Client client) {
		return clientService.create(client);
	}
	
}
