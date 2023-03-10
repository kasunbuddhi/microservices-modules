package com.programmingtechie.inventoryservice.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtechie.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public boolean isInStock(String skuCode) {
		List<String> items= Stream.of(skuCode.split(","))
				.map(String::trim)
				.collect(Collectors.toList());

		Boolean inStock = true;
		for (String obj : items) {
			if (!inventoryRepository.findBySkuCode(obj).isPresent()) {
				inStock = false;
			}
		}

		return inStock;

	}

}
