package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.domain.entity.Product;
import io.github.diegoroberto.domain.repository.ProductRepository;
import io.github.diegoroberto.exception.BusinessException;
import io.github.diegoroberto.exception.NoResultsException;
import io.github.diegoroberto.rest.dto.ProductDTO;
import io.github.diegoroberto.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public Optional<ProductDTO> findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            ProductDTO clientDTO = modelMapper.map(optional.get(), ProductDTO.class);
            return Optional.ofNullable(clientDTO);
        } else {
            throw new BusinessException("Cliente não encontrado.");
        }
    }

    @Override
    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public void delete(ProductDTO dto) {
        productRepository.delete(modelMapper.map(dto, Product.class));
    }

    @Override
    public List<ProductDTO> findAll(Example<ProductDTO> example) {
        if (example == null) {
            throw new IllegalArgumentException("parâmetro não pode ser nulo.");
        }
        List<Product> products = productRepository.findAll((Example) example);

        if (products.isEmpty()) {
            throw new NoResultsException("Nenhum produto encontrado com os parâmetros fornecidos.");
        }
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
