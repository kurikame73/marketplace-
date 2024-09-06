package com.example.marketplace.domain.Item.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataMigrationRunner implements CommandLineRunner {
    private final ItemMigrationService itemMigrationService;

    public DataMigrationRunner(ItemMigrationService itemMigrationService) {
        this.itemMigrationService = itemMigrationService;
    }

    @Override
    public void run(String... args) throws Exception {
        itemMigrationService.migrateDataToSearchServer();
    }
}
