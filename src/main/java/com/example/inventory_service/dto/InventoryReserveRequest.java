package com.example.inventory_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReserveRequest {
    private String productCode;
    private int quantity;
}