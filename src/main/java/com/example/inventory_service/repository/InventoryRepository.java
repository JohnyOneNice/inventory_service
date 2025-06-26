package com.example.inventory_service.repository;

import com.example.inventory_service.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<InventoryItem, java.util.UUID> {
}