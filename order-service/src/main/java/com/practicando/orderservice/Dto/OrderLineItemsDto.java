package com.practicando.orderservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private Long Id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
