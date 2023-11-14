package io.github.diegoroberto.service;

import io.github.diegoroberto.rest.dto.ProductDTO;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDTO> findById(Long id);

    ProductDTO save(ProductDTO dto);

    void delete(ProductDTO dto);

    List<ProductDTO> findAll(Example<ProductDTO> example);
}
