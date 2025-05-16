package com.br.macros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.macros.models.Pessoa;
import com.br.macros.records.LoginRequestDto;
import com.br.macros.services.PessoaService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {
	 @Autowired
	    private PessoaService pessoaService;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
	        Pessoa pessoa = pessoaService.buscarPorEmail(request.email());
	        
	        if (pessoa != null && pessoa.getSenha().equals(request.senha())) {
	            return ResponseEntity.ok("Login realizado com sucesso");
	        }

	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	    }
}
