package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.rest.dto.ProductDTO;
import io.github.diegoroberto.service.implement.ProductServiceImpl;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api("API Produtos")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    private static final String NOT_FOUND = "Produto não encontrado";
    private static final String FOUND = "Produto encontrado";


    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = FOUND),
            @ApiResponse(code = 404, message = NOT_FOUND + "para o ID informado")
    })
    public ProductDTO getProductById(
            @PathVariable
            @ApiParam("Id do produto") Long id){
        return productService
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public ProductDTO save(@RequestBody @Valid ProductDTO dto){
        return productService.save(dto);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um novo produto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long id){
        productService.findById(id)
                .map(product -> {
                    productService.delete(product);
                    return product;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND) );

    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um novo produto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Long id,
                        @RequestBody @Valid ProductDTO dto){
        productService
                .findById(id)
                .map(productExistente -> {
                    dto.setId(productExistente.getId());
                    productService.save(dto);
                    return productExistente;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND) );
    }

    @GetMapping
    public List<ProductDTO> find(ProductDTO params){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example<ProductDTO> example = Example.of(params, matcher);
        return productService.findAll(example);
    }

}
