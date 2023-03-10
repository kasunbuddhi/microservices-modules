package com.programmingtechie.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	private String skuCodeList;
	
	public void placeOrder(OrderRequest orderRequest) {
		skuCodeList = "";
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
		.stream()
		.map(this::mapToOrder)
		.toList();
		
		order.setOrderLineItemsList(orderLineItems);

		/** call inventory service and place order if product in stock */
		Boolean isInStock = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
				.retrieve()
				.bodyToMono(Boolean.class)
				.block();

		if (isInStock) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException(order.getOrderLineItemsList().get(0).getSkuCode() + " not in stocks");
		}

	}

	private OrderLineItems mapToOrder(OrderLineItemsDto dto) {
		OrderLineItems items = new OrderLineItems();
		items.setPrice(dto.getPrice());
		items.setSkuCode(dto.getSkuCode());
		items.setQuantity(dto.getQuantity());
		if(StringUtils.hasText(skuCodeList)){
			skuCodeList += "," + dto.getSkuCode();
		} else {
			skuCodeList = dto.getSkuCode();
		}
		
		return items;
	}

}
