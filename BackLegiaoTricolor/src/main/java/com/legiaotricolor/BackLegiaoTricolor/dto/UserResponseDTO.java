package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.legiaotricolor.BackLegiaoTricolor.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String name;
    private String cpf;
    private String telefone;
    private String email;
    private Role role;

    public UserResponseDTO(UUID id, String name, String email, Role role) {
    }
}
