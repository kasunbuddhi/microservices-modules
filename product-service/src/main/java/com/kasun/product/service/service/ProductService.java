package com.kasun.product.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasun.product.service.dto.ProductRequest;
import com.kasun.product.service.dto.ProductResponse;
import com.kasun.product.service.model.Product;
import com.kasun.product.service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	@Autowired
	private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {
		Product product = Product.productBuilder().name(productRequest.getName())
				.description(productRequest.getDescription()).price(productRequest.getPrice()).build();
		
		productRepository.save(product);
		log.info("product Id : {}", product.getId());
		
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(product -> mapToProductResponse(product)).toList();
	}

	private ProductResponse mapToProductResponse(Product product) {
		ProductResponse response = ProductResponse.builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).build();
		return response;
	}

}
