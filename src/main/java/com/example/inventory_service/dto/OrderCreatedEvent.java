package com.example.inventory_service.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private UUID orderId;
    private UUID userId;
    private UUID productId;
    private int productCount;
    private int price;
    private String status;
    private String idempotencyKey;
} 