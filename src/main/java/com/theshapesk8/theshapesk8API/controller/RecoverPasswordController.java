package com.theshapesk8.theshapesk8API.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theshapesk8.theshapesk8API.model.Client;
import com.theshapesk8.theshapesk8API.model.RecoverPassword;
import com.theshapesk8.theshapesk8API.service.ClientServices;
import com.theshapesk8.theshapesk8API.service.EmailService;

@RestController
@RequestMapping("/recoverPassword")
@CrossOrigin(origins = "*")
public class RecoverPasswordController {

    @Autowired
    private ClientServices clientService;

    @Autowired
    private EmailService emailService;
    
    private Logger logger = Logger.getLogger(ClientServices.class.getName());

    private List<RecoverPassword> userCodeEmailAndDataGenerate = new ArrayList<>();

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    @PostMapping(value = "/requestToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> requestToken(@RequestBody Map<String, String> request) {
        String emailToFind = request.get("to");

        if (emailToFind == null || emailToFind.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing email");
        }

        Client clientFinded = clientService.findByEmail(emailToFind);

        if (clientFinded != null) {
            String recoverCode = generateVerificationCode();
            LocalDateTime timestamp = LocalDateTime.now();

            userCodeEmailAndDataGenerate.add(new RecoverPassword(recoverCode, emailToFind, timestamp));

            String subject = "Recuperação de conta";
            String text = "Olá " + clientFinded.getNome() + ", Seu código de recuperação é " + recoverCode + ". Este código é válido por 10 minutos.";

            emailService.sendSimpleEmail(emailToFind, subject, text);

            return ResponseEntity.ok("Email sent successfully");
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

        Optional<RecoverPassword> recoverPasswordOpt = userCodeEmailAndDataGenerate.stream()
                .filter(rp -> rp.getCodigo().equals(receivedToken))
                .findFirst();

        if (recoverPasswordOpt.isPresent()) {
            RecoverPassword recoverPassword = recoverPasswordOpt.get();
            long minutesSinceTokenGeneration = recoverPassword.getHorarioQueFoiGerado()
                    .until(LocalDateTime.now(), java.time.temporal.ChronoUnit.MINUTES);

            if (minutesSinceTokenGeneration <= 10) {
                return ResponseEntity.ok("Token is valid");
            } else {
                userCodeEmailAndDataGenerate.remove(recoverPassword);
                return ResponseEntity.badRequest().body("Token expired");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    @PostMapping(value = "/alterPassword", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> alterPassword(@RequestBody Map<String, String> request) {
        String emailToFind = request.get("to");
        String newPassword = request.get("newpassword");

        if (emailToFind == null || emailToFind.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing email or new password");
        }

        Optional<RecoverPassword> recoverPasswordOpt = userCodeEmailAndDataGenerate.stream()
                .filter(rp -> rp.getEmail().equals(emailToFind))
                .findFirst();

        if (recoverPasswordOpt.isPresent()) {
            RecoverPassword recoverPassword = recoverPasswordOpt.get();
            Client clientFinded = clientService.findByEmail(emailToFind);
            if (clientFinded != null) {
                clientFinded.setSenha(newPassword);
                clientService.update(clientFinded);
                userCodeEmailAndDataGenerate.remove(recoverPassword); // Remove o código de recuperação após alterar a senha
                return ResponseEntity.ok("Password updated successfully");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token for the email");
        }
    }
}
