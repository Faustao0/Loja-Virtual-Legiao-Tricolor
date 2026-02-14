package com.legiaotricolor.BackLegiaoTricolor.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;
}
