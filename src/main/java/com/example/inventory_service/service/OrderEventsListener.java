package com.example.inventory_service.service;

import com.example.inventory_service.dto.OrderCreatedEvent;
import com.example.inventory_service.dto.InventoryReserveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventsListener {
    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-events", groupId = "inventory-service-v3")
    public void handleOrderCreated(OrderCreatedEvent event) {
        if (inventoryService.hasSufficientInventory(event.getProductId(), event.getProductCount())) {
            try {
                inventoryService.reserve(new InventoryReserveRequest(event.getProductId(), event.getProductCount()));
                inventoryService.publishInventoryReserved(event);
            } catch (RuntimeException e) {
                inventoryService.publishInventoryReservationFailed(event.getProductId(), event.getProductCount());
            }
        } else {
            inventoryService.publishInventoryReservationFailed(event.getProductId(), event.getProductCount());
        }
    }
} 