package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.dto.AdminLoginRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.LoginRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.LoginResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO dto
    ) throws BusinessException {

        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(
            @RequestBody @Valid AdminLoginRequestDTO dto
    ) throws BusinessException {

        return ResponseEntity.ok(authService.adminLogin(dto));
    }
}