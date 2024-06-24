package com.theshapesk8.theshapesk8API.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
import com.theshapesk8.theshapesk8API.service.EmailService;

@RestController
@RequestMapping("/client")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class ClientController {
	
	@Autowired
	private ClientServices clientService;
	
	@Autowired
    private EmailService emailService;
	
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
	
	/*@PostMapping(value = "/requestToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requstToken(@RequestBody String emailToSend) {
		
        if (emailToSend == null) {
            return ResponseEntity.badRequest().body("Missing email");
        }
        
        String userName = clientService.findByEmail(emailToSend).getNome();
        
        if (userName != null) {
        	
        	Random random = new Random();
            int code = 100000 + random.nextInt(900000); // Gera um número aleatório entre 100000 e 999999
            String recoverCode = String.valueOf(code);
            
            String subject = "Recuperação de conta";
            String text = "Olá "+userName+", Seu codigo de recuperação é "+recoverCode+" esese codigo tem validade de 10 minutos. Nunca informe seus dados de acesso pra outras pessoas.";
        	
            emailService.sendSimpleEmail(emailToSend, subject, text);
            return ResponseEntity.ok("Email sent successfully");
        } else {
        	return ResponseEntity.ok("Email sent successfully");
        }
	};
	
	@PostMapping(value = "/recoverPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> recoverPassword() {
		
		
		return ResponseEntity.ok("aa");
	};*/
	
	private final Map<String, LocalDateTime> tokenMap = new ConcurrentHashMap<>();
	
	private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
	
	@PostMapping(value = "/requestToken", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> requestToken(@RequestBody Map<String, String> request) {
	    
		String emailToFind= request.get("to");
		
		if (emailToFind == null || emailToFind.isEmpty()) {
	        return ResponseEntity.badRequest().body("Missing email");
	    }
		
		//String emailToFind = emailToSend.substring(14, emailToSend.length() - 4);

	    Client client = clientService.findByEmail(emailToFind);

	    if (client != null) {
	        String userName = client.getNome();
	        if (userName != null) {
	            String recoverCode = generateVerificationCode();

	            LocalDateTime timestamp = LocalDateTime.now();
	            tokenMap.put(recoverCode, timestamp);

	            String subject = "Recuperação de conta";
	            String text = "Olá " + userName + ", Seu código de recuperação é " + recoverCode + ". Este código é válido por 10 minutos.";

	            emailService.sendSimpleEmail(emailToFind, subject, text);

	            return ResponseEntity.ok("Email sent successfully");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	 
	 @PostMapping(value = "/verifyToken", consumes = "application/json", produces = "application/json")
	    public ResponseEntity<String> verifyToken(@RequestBody Map<String, String> request) {
	        String receivedToken = request.get("token");
	        
	        if (receivedToken == null || receivedToken.isEmpty()) {
	            return ResponseEntity.badRequest().body("Missing token");
	        }

	        LocalDateTime timestamp = tokenMap.get(receivedToken);

	        if (timestamp != null) {
	            long minutesSinceTokenGeneration = timestamp.until(LocalDateTime.now(), java.time.temporal.ChronoUnit.MINUTES);

	            if (minutesSinceTokenGeneration <= 10) {
	                return ResponseEntity.ok("Token is valid");
	            } else {
	                tokenMap.remove(receivedToken);
	                return ResponseEntity.badRequest().body("Token expired");
	            }
	        } else {
	            return ResponseEntity.badRequest().body("Invalid token");
	        }
	    }
	
}
