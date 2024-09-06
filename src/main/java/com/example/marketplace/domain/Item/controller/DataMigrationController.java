package com.example.marketplace.domain.Item.controller;

import com.example.marketplace.domain.Item.service.ItemMigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataMigrationController {
    private final ItemMigrationService itemMigrationService;

    @PostMapping("/to-search-server")
    public ResponseEntity<String> migrateToSearchServer() {
        itemMigrationService.migrateDataToSearchServer();
        return ResponseEntity.ok("Data migration to search server initiated.");
    }
}
