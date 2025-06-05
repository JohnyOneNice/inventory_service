package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryReserveRequest;
import com.example.inventory_service.dto.ProductReleaseRequest;
import com.example.inventory_service.model.InventoryItem;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void reserve(InventoryReserveRequest request) {
        InventoryItem item = inventoryRepository.findByProductCode(request.getProductCode())
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        if (item.getAvailableCount() < request.getQuantity()) {
            throw new RuntimeException("Недостаточно товара на складе");
        }

        item.setAvailableCount(item.getAvailableCount() - request.getQuantity());
        inventoryRepository.save(item);
    }

    public void release(ProductReleaseRequest request) {
        InventoryItem item = inventoryRepository.findByProductCode(request.getProductCode())
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
        item.setAvailableCount(item.getAvailableCount() + request.getQuantity());
        inventoryRepository.save(item);
    }
}