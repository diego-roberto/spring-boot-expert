package io.github.diegoroberto.service;

import io.github.diegoroberto.domain.enums.OrderStatus;
import io.github.diegoroberto.rest.dto.OrderDTO;
import io.github.diegoroberto.rest.dto.OrderInfoDTO;

public interface OrderService {
    Long save(OrderDTO dto);
    OrderInfoDTO getFullOrder(Long id);
    void updateStatus(Long id, OrderStatus status);
}
