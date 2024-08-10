package com.orderinventoryapp.inventory_service.repository;

import com.orderinventoryapp.inventory_service.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
