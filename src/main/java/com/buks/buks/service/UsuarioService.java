package com.buks.buks.service;

import com.buks.buks.dto.UsuarioDTO;
import com.buks.buks.model.Usuario;
import com.buks.buks.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO save(UsuarioDTO dto) {
        Usuario usuario = toEntity(dto);
        Usuario saved = usuarioRepository.save(usuario);
        return toDTO(saved);
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> update(Integer id, UsuarioDTO dto) {
        return usuarioRepository.findById(id)
                .map(existing -> {
                    Usuario usuario = toEntity(dto);
                    usuario.setId(id);
                    return toDTO(usuarioRepository.save(usuario));
                });
    }

    public boolean delete(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // conversões
    private Usuario toEntity(UsuarioDTO dto) {
        return new Usuario(dto.getId(), dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getTelefone());
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());
    }
}
