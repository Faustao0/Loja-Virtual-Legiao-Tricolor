package com.legiaotricolor.BackLegiaoTricolor.service;

import com.legiaotricolor.BackLegiaoTricolor.domain.Order;
import com.legiaotricolor.BackLegiaoTricolor.domain.OrderItem;
import com.legiaotricolor.BackLegiaoTricolor.domain.Product;
import com.legiaotricolor.BackLegiaoTricolor.domain.User;
import com.legiaotricolor.BackLegiaoTricolor.enums.OrderStatus;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.repository.OrderItemRepository;
import com.legiaotricolor.BackLegiaoTricolor.repository.OrderRepository;
import com.legiaotricolor.BackLegiaoTricolor.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingService shippingService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, ShippingService shippingService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.shippingService = shippingService;
    }

    public Order getOrCreateOrder(User user) {
        return orderRepository
                .findByUserIdAndStatus(user.getId(), OrderStatus.CREATED)
                .orElseGet(() -> orderRepository.save(
                        Order.builder()
                                .user(user)
                                .status(OrderStatus.CREATED)
                                .build()
                ));
    }

    @Transactional
    public Order addItemToCart(User user, UUID productId, Integer quantity)
            throws BusinessException {

        Order order = getOrCreateOrder(user);

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessException("Pedido não pode ser alterado");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        if (!product.getActive()) {
            throw new BusinessException("Produto indisponível");
        }

        if (product.getStockQuantity() < quantity) {
            throw new BusinessException("Estoque insuficiente");
        }

        OrderItem item = order.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .price(product.getPrice())
                    .quantity(quantity)
                    .build();
            order.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }

        order.calculateTotal();

        return orderRepository.save(order);
    }



    @Transactional
    public void checkout(Order order) throws BusinessException {

        if (order.getItems().isEmpty()) {
            throw new BusinessException("Pedido sem itens");
        }

        order.calculateTotal();
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        orderRepository.save(order);
    }

    @Transactional
    public void confirmPayment(Order order) throws BusinessException {

        if (order.getStatus() != OrderStatus.WAITING_PAYMENT) {
            throw new BusinessException("Pedido não está aguardando pagamento");
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BusinessException(
                        "Estoque insuficiente para " + product.getName()
                );
            }

            product.setStockQuantity(
                    product.getStockQuantity() - item.getQuantity()
            );

            productRepository.save(product);
        }

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Transactional
    public void updateItem(UUID itemId, Integer quantity)
            throws BusinessException {

        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException("Item não encontrado"));

        Order order = item.getOrder();

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessException("Pedido não pode ser alterado");
        }

        if (quantity <= 0) {
            order.getItems().remove(item);
            orderItemRepository.delete(item);
        } else {
            if (item.getProduct().getStockQuantity() < quantity) {
                throw new BusinessException("Estoque insuficiente");
            }
            item.setQuantity(quantity);
        }

        order.calculateTotal();
        orderRepository.save(order);
    }

    @Transactional
    public Order applyShipping(User user) {

        Order order = getOrCreateOrder(user);

        if (order.getItems().isEmpty()) {
            throw new BusinessException("Pedido sem itens");
        }

        order.setShippingAmount(
                shippingService.calculateFixedShipping()
        );

        order.calculateTotal();

        return orderRepository.save(order);
    }
}
