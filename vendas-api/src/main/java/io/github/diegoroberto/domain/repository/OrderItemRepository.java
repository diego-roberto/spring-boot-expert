package io.github.diegoroberto.domain.repository;

import io.github.diegoroberto.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
