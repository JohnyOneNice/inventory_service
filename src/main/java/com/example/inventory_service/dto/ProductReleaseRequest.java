package com.example.inventory_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReleaseRequest {
    private String productCode;
    private int quantity;
}
