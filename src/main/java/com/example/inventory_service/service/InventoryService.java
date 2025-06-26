package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryReserveRequest;
import com.example.inventory_service.dto.ProductReleaseRequest;
import com.example.inventory_service.model.InventoryItem;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.inventory_service.dto.InventoryReservedEvent;
import com.example.inventory_service.dto.InventoryReservationFailedEvent;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void reserve(InventoryReserveRequest request) {
        InventoryItem item = inventoryRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        if (item.getAvailableCount() < request.getQuantity()) {
            throw new RuntimeException("Недостаточно товара на складе");
        }

        item.setAvailableCount(item.getAvailableCount() - request.getQuantity());
        inventoryRepository.save(item);
    }

    public void release(ProductReleaseRequest request) {
        InventoryItem item = inventoryRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
        item.setAvailableCount(item.getAvailableCount() + request.getQuantity());
        inventoryRepository.save(item);
    }

    public InventoryItem createItem(String productName, int count) {
        InventoryItem item = InventoryItem.builder()
                .productName(productName)
                .availableCount(count)
                .build();
        return inventoryRepository.save(item);
    }

    public boolean hasSufficientInventory(UUID id, int count) {
        return inventoryRepository.findById(id)
                .map(item -> item.getAvailableCount() >= count)
                .orElse(false);
    }

    public void publishInventoryReserved(UUID id, int count) {
        InventoryReservedEvent event = new InventoryReservedEvent(id, count);
        kafkaTemplate.send("inventory-events", event);
    }

    public void publishInventoryReservationFailed(UUID id, int count) {
        InventoryReservationFailedEvent event = new InventoryReservationFailedEvent(id, count);
        kafkaTemplate.send("inventory-events", event);
    }
}