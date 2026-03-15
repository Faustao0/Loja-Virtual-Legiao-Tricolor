package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.domain.User;
import com.legiaotricolor.BackLegiaoTricolor.dto.CheckoutRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.CheckoutResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Checkout do pedido
     */
    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(
            @RequestBody @Valid CheckoutRequestDTO dto
    ) throws BusinessException {

        // ⚠️ TEMPORÁRIO
        // Depois isso virá do JWT (usuário autenticado)
        User userMock = mockAuthenticatedUser();

        CheckoutResponseDTO response =
                paymentService.checkout(userMock, dto);

        return ResponseEntity.ok(response);
    }

    /**
     * MOCK TEMPORÁRIO DO USUÁRIO LOGADO
     * (será removido quando implementarmos JWT)
     */
    private User mockAuthenticatedUser() {
        User user = new User();
        user.setId(
                java.util.UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
        return user;
    }
}
