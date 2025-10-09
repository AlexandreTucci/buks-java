package com.buks.buks.controller;

import com.buks.buks.model.Usuario;
import com.buks.buks.repository.UsuarioRepository;
import com.buks.buks.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(usuario.getEmail());
        if (userOpt.isPresent() && encoder.matches(usuario.getSenha(), userOpt.get().getSenha())) {
            String token = jwtService.generateToken(userOpt.get());

            // Crie um Map para encapsular o token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            // Retorne o Map, que será convertido para JSON
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
