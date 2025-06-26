package com.example.inventory_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReserveRequest {
    private java.util.UUID id;
    private int quantity;
}