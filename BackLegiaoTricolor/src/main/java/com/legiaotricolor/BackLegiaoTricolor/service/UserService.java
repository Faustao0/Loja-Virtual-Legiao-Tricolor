package com.legiaotricolor.BackLegiaoTricolor.service;

import com.legiaotricolor.BackLegiaoTricolor.domain.User;
import com.legiaotricolor.BackLegiaoTricolor.dto.UserCreateDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.UserResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.UserUpdateDTO;
import com.legiaotricolor.BackLegiaoTricolor.enums.Role;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createClient(UserCreateDTO dto) {
        validateEmail(dto.getEmail());

        User user = new User();
        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.CLIENT);
        user.setActive(true);

        userRepository.save(user);
        return toResponseDTO(user);
    }

    public UserResponseDTO createAdmin(UserCreateDTO dto) {
        validateEmail(dto.getEmail());

        User user = new User();
        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ADMIN);
        user.setActive(true);

        userRepository.save(user);
        return toResponseDTO(user);
    }

    /* ======================
       READ
       ====================== */

    public UserResponseDTO findById(UUID id) {
        return toResponseDTO(findUser(id));
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO update(UUID id, UserUpdateDTO dto) {
        User user = findUser(id);

        if (!user.isActive()) {
            throw new BusinessException("Usuário inativo");
        }

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);
        return toResponseDTO(user);
    }

    public void deactivate(UUID id) {
        User user = findUser(id);

        if (!user.isActive()) {
            throw new BusinessException("Usuário já está inativo");
        }

        user.setActive(false);
        userRepository.save(user);
    }

    private User findUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("E-mail já cadastrado");
        }
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

