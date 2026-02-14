package com.legiaotricolor.BackLegiaoTricolor.service;

import com.legiaotricolor.BackLegiaoTricolor.domain.User;
import com.legiaotricolor.BackLegiaoTricolor.dto.AdminLoginRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.LoginRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.LoginResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.enums.Role;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponseDTO login(LoginRequestDTO dto) throws BusinessException {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException("E-mail ou senha inválidos"));

        if (!user.isActive()) {
            throw new BusinessException("Usuário desativado");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("E-mail ou senha inválidos");
        }

        // Por enquanto o token será mockado
        String token = "TOKEN_MOCKADO";

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }

    @Value("${admin.access-code}")
    private String adminAccessCode;

    public LoginResponseDTO adminLogin(AdminLoginRequestDTO dto) {

        if (!dto.adminCode().equals(adminAccessCode)) {
            throw new BusinessException("Código administrativo inválido");
        }

        User admin = userRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new BusinessException("Administrador não encontrado"));

        if (admin.getRole() != Role.ADMIN) {
            throw new BusinessException("Usuário não é administrador");
        }

        if (!passwordEncoder.matches(dto.password(), admin.getPassword())) {
            throw new BusinessException("Credenciais inválidas");
        }

        String token = jwtTokenService.generateToken(admin);

        return new LoginResponseDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole(),
                token
        );
    }
}
