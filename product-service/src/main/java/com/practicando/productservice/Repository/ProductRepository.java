package com.practicando.productservice.Repository;

import com.practicando.productservice.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
