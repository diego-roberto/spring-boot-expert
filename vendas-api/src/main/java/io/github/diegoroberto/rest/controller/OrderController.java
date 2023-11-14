package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.domain.enums.OrderStatus;
import io.github.diegoroberto.rest.dto.OderStatusUpdateDTO;
import io.github.diegoroberto.rest.dto.OrderDTO;
import io.github.diegoroberto.rest.dto.OrderInfoDTO;
import io.github.diegoroberto.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Api("API Pedidos")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private static final String NOT_FOUND = "Pedido não encontrado";
    private static final String FOUND = "Pedido encontrado";

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = FOUND),
            @ApiResponse(code = 404, message = NOT_FOUND + "para o ID informado")
    })
    public OrderInfoDTO getById(@PathVariable Long id) {
        return orderService.getFullOrder(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Long id,
                             @RequestBody OderStatusUpdateDTO dto) {
        String novoStatus = dto.getNewStatus();
        orderService.updateStatus(id, OrderStatus.valueOf(novoStatus));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "pedido salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Long save(@RequestBody @Valid OrderDTO dto) {
        return orderService.save(dto);
    }
}
