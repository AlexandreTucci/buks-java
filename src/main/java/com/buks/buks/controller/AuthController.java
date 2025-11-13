package com.buks.buks.controller;

import com.buks.buks.exception.BusinessException;
import com.buks.buks.exception.ErrorCode;
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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        // Verifica se o e-mail já existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Criptografa a senha antes de salvar
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário registrado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(usuario.getEmail());

        // Caso o usuário não exista
        if (userOpt.isEmpty()) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Usuario user = userOpt.get();

        // Caso a senha esteja incorreta
        if (!encoder.matches(usuario.getSenha(), user.getSenha())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        // Gera o token JWT
        String token = jwtService.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
