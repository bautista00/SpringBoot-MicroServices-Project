package com.practicando.orderservice.Service;


import com.practicando.orderservice.Dto.InventoryResponse;
import com.practicando.orderservice.Dto.OrderLineItemsDto;
import com.practicando.orderservice.Dto.OrderRequest;
import com.practicando.orderservice.Model.Order;
import com.practicando.orderservice.Model.OrderLineItems;
import com.practicando.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                    .map(OrderLineItems::getSkuCode)
                    .toList();

        //Llamado al inventory service y capturar una orden si el
        // producto esta en stock
        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
            return "Order placed successfully";
        } else{
            throw new IllegalArgumentException("Product is out of stock, please try again later.");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems =  new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }


}
