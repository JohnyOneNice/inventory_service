package com.example.inventory_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReleaseRequest {
    private java.util.UUID id;
    private int quantity;
}
