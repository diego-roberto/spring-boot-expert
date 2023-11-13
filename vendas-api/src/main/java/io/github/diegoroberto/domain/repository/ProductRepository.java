package io.github.diegoroberto.domain.repository;

import io.github.diegoroberto.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
