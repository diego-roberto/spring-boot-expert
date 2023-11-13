package io.github.diegoroberto.rest.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO {
        private Long id;
        private String cpf;
        private String clientName;
        private BigDecimal total;
        private String orderDate;
        private String status;
        private List<OrderItemInfoDTO> items;
}
