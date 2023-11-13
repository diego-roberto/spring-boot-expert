package io.github.diegoroberto.rest.dto;

import io.github.diegoroberto.util.validation.NotEmptyList;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

        @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
        private Long client;

        @NotNull(message = "{campo.total-pedido.obrigatorio}")
        private BigDecimal total;

        @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
        private List<OrderItemDTO> items;

}