package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.domain.Order;
import com.legiaotricolor.BackLegiaoTricolor.domain.OrderItem;
import com.legiaotricolor.BackLegiaoTricolor.domain.Product;
import com.legiaotricolor.BackLegiaoTricolor.domain.User;
import com.legiaotricolor.BackLegiaoTricolor.dto.AddItemRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.OrderItemResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.OrderResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.UpdateItemRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.repository.ProductRepository;
import com.legiaotricolor.BackLegiaoTricolor.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductRepository productRepository;

    // 🔹 Buscar carrinho atual
    @GetMapping("/cart")
    public OrderResponseDTO getCart(
            @AuthenticationPrincipal User user
    ) {
        Order order = orderService.getOrCreateOrder(user);
        return toResponse(order);
    }

    // 🔹 Adicionar item ao carrinho
    @PostMapping("/cart/items")
    public OrderResponseDTO addItem(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddItemRequestDTO dto
    ) throws BusinessException {

        Order updatedOrder = orderService
                .addItemToCart(user, dto.productId(), dto.quantity());

        return toResponse(updatedOrder);
    }

    // 🔹 Atualizar quantidade do item
    @PutMapping("/cart/items/{itemId}")
    public OrderResponseDTO updateItem(
            @AuthenticationPrincipal User user,
            @PathVariable UUID itemId,
            @RequestBody @Valid UpdateItemRequestDTO dto
    ) throws BusinessException {

        Order order = orderService.getOrCreateOrder(user);
        orderService.updateItem(itemId, dto.quantity());

        return toResponse(order);
    }

    // 🔹 Finalizar carrinho (vai para pagamento)
    @PostMapping("/cart/checkout")
    public void checkout(
            @AuthenticationPrincipal User user
    ) throws BusinessException {

        Order order = orderService.getOrCreateOrder(user);
        orderService.checkout(order);
    }

    // ======================
    // Mapper interno
    // ======================
    private OrderResponseDTO toResponse(Order order) {

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                items
        );
    }

    private OrderItemResponseDTO toItemResponse(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubtotal()
        );
    }

    @PatchMapping("/cart/shipping")
    public OrderResponseDTO applyShipping(
            @AuthenticationPrincipal User user
    ) {
        Order order = orderService.applyShipping(user);
        return toResponse(order);
    }
}
