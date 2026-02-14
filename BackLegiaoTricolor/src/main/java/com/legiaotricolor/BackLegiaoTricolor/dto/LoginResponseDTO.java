package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.legiaotricolor.BackLegiaoTricolor.enums.Role;

import java.util.UUID;

public record LoginResponseDTO(
        UUID id,
        String name,
        String email,
        Role role,
        String token
) {}
