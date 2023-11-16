package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.config.InternationalizationConfig;
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

    private final InternationalizationConfig i18n;

    @Override
    public Optional<ProductDTO> findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            ProductDTO clientDTO = modelMapper.map(optional.get(), ProductDTO.class);
            return Optional.ofNullable(clientDTO);
        } else {
            throw new BusinessException(i18n.getMessage("msg.product.not-found"));
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
            throw new IllegalArgumentException(i18n.getMessage("msg.validation.null"));
        }

        Example<Product> exampleEntity = Example.of(modelMapper.map(example, Product.class));
        List<Product> products = productRepository.findAll(exampleEntity);

        if (products.isEmpty()) {
            throw new NoResultsException(i18n.getMessage("msg.validation.not-found"));
        }
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
