package com.orderinventoryapp.order_service.service;

import com.orderinventoryapp.inventory_service.models.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class InventoryServiceClient {
    private final RestTemplate restTemplate;

    @Autowired
    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<Inventory> getInventoryById(Long id) {
        try {
            Inventory inventory = restTemplate.getForObject("http://localhost:8082/inventory/" + id, Inventory.class);
            return Optional.ofNullable(inventory);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
