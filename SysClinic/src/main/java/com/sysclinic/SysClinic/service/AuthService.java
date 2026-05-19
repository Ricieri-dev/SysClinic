package com.sysclinic.SysClinic.service;

import com.sysclinic.SysClinic.dto.LoginRequestDTO;
import com.sysclinic.SysClinic.dto.RegisterRequestDTO;
import com.sysclinic.SysClinic.model.Usuario;
import com.sysclinic.SysClinic.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(RegisterRequestDTO dto) {

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .perfil(dto.getPerfil())
                .ativo(true)
                .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean senhaValida = passwordEncoder.matches(
                dto.getSenha(),
                usuario.getSenha()
        );

        if (!senhaValida) {
            throw new RuntimeException("Senha inválida");
        }

        return usuario;
    }
}