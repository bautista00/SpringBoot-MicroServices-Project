package com.practicando.productservice.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("product")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

}
