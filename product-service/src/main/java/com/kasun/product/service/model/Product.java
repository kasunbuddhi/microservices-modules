package com.kasun.product.service.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName="productBuilder")
@Data
@Document(value="product")
public class Product {

	@Id
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
}
