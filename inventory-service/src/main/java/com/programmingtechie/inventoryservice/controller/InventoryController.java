package com.programmingtechie.inventoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.programmingtechie.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public boolean isInStock(@RequestParam String skuCode) {
		return inventoryService.isInStock(skuCode);
	}

}
