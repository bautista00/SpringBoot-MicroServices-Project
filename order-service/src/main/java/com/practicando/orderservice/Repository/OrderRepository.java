package com.practicando.orderservice.Repository;

import com.practicando.orderservice.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
