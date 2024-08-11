package com.orderinventoryapp.inventory_service.service;

import com.orderinventoryapp.inventory_service.models.Inventory;
import com.orderinventoryapp.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory updateInventoryStock(Long id, int newStock) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);

        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            inventory.setStock(newStock);
            return inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("Inventory item with ID " + id + " not found.");
        }
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }
}
