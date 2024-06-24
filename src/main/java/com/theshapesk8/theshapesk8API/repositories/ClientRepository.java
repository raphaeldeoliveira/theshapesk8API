package com.theshapesk8.theshapesk8API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theshapesk8.theshapesk8API.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findByCpfAndSenha(String cpf, String senha);
    Client findByEmailAndSenha(String email, String senha);
	
}
