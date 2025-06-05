package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryReserveRequest;
import com.example.inventory_service.dto.ProductReleaseRequest;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@RequestBody InventoryReserveRequest request) {
        try {
            inventoryService.reserve(request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/release")
    public ResponseEntity<Void> release(@RequestBody ProductReleaseRequest request) {
        try {
            inventoryService.release(request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}