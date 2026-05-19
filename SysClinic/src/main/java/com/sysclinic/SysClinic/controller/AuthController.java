package com.sysclinic.SysClinic.controller;

import com.sysclinic.SysClinic.dto.LoginRequestDTO;
import com.sysclinic.SysClinic.dto.RegisterRequestDTO;
import com.sysclinic.SysClinic.model.Usuario;
import com.sysclinic.SysClinic.service.AuthService;
import com.sysclinic.SysClinic.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> registrar(
            @Valid @RequestBody RegisterRequestDTO dto) {

        Usuario usuario = authService.registrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        String token = authService.autenticar(dto);

        LoginResponseDTO response =
                LoginResponseDTO.builder()
                        .token(token)
                        .build();

        return ResponseEntity.ok(response);
    }
}