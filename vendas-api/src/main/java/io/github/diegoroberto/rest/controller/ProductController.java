package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.constant.MessageConstants;
import io.github.diegoroberto.rest.dto.ProductDTO;
import io.github.diegoroberto.service.implement.ProductServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Api("API Produtos")
public class ProductController {

    private final ProductServiceImpl productService;


    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = MessageConstants.MSG_PRODUCT_FOUND),
            @ApiResponse(code = 404, message = MessageConstants.MSG_PRODUCT_NOT_FOUND),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
    })
    public ProductDTO getProductById(
            @PathVariable
            @ApiParam("Id do produto") Long id){
        return productService
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @ApiOperation("Buscar produtos com parâmetros")
    @ApiResponses({
            @ApiResponse(code = 200, message = MessageConstants.MSG_PRODUCT_FOUND),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
            @ApiResponse(code = 404, message = MessageConstants.MSG_PRODUCT_NONE_FOUND)

    })
    public List<ProductDTO> find(ProductDTO params){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example<ProductDTO> example = Example.of(params, matcher);
        return productService.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = MessageConstants.MSG_PRODUCT_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
    })
    public ProductDTO save(@RequestBody @Valid ProductDTO dto){
        return productService.save(dto);
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um produto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(code = 200, message = MessageConstants.MSG_PRODUCT_SUCCESS),
            @ApiResponse(code = 201, message = MessageConstants.MSG_PRODUCT_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
            @ApiResponse(code = 404, message = MessageConstants.MSG_PRODUCT_NOT_FOUND)
    })
    public void update( @PathVariable Long id,
                        @RequestBody @Valid ProductDTO dto){
        productService
                .findById(id)
                .map(found -> {
                    dto.setId(found.getId());
                    productService.save(dto);
                    return found;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um produto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(code = 204, message = MessageConstants.MSG_PRODUCT_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
            @ApiResponse(code = 404, message = MessageConstants.MSG_PRODUCT_NOT_FOUND)
    })
    public void delete( @PathVariable Long id){
        productService.findById(id)
                .map(product -> {
                    productService.delete(product);
                    return product;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}
