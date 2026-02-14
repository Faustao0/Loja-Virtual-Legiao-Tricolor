package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.dto.UserCreateDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.UserResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.UserUpdateDTO;
import com.legiaotricolor.BackLegiaoTricolor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ======================
       CREATE
       ====================== */

    @PostMapping("/client")
    public ResponseEntity<UserResponseDTO> createClient(
            @RequestBody @Valid UserCreateDTO dto) {

        UserResponseDTO response = userService.createClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> createAdmin(
            @RequestBody @Valid UserCreateDTO dto) {

        UserResponseDTO response = userService.createAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* ======================
       READ
       ====================== */

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /* ======================
       UPDATE
       ====================== */

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        return ResponseEntity.ok(userService.update(id, dto));
    }

    /* ======================
       DELETE (SOFT)
       ====================== */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
