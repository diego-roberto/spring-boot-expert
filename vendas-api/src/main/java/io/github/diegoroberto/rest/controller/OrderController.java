package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.constant.MessageConstants;
import io.github.diegoroberto.domain.enums.OrderStatus;
import io.github.diegoroberto.rest.dto.OderStatusUpdateDTO;
import io.github.diegoroberto.rest.dto.OrderDTO;
import io.github.diegoroberto.rest.dto.OrderInfoDTO;
import io.github.diegoroberto.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Api("API Pedidos")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = MessageConstants.MSG_ORDER_FOUND),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
            @ApiResponse(code = 404, message = MessageConstants.MSG_ORDER_NOT_FOUND)
    })
    public OrderInfoDTO getById(@PathVariable Long id) {
        return orderService.getFullOrder(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar status do pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = MessageConstants.MSG_ORDER_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED)
    })
    public void updateStatus(@PathVariable Long id,
                             @RequestBody OderStatusUpdateDTO dto) {
        String novoStatus = dto.getNewStatus();
        orderService.updateStatus(id, OrderStatus.valueOf(novoStatus));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = MessageConstants.MSG_ORDER_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_UNAUTHORIZED),
    })
    public Long save(@RequestBody @Valid OrderDTO dto) {
        return orderService.save(dto);
    }
}
