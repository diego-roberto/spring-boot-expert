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

        @NotNull(message = "{field.client.required}")
        private Long client;

        @NotNull(message = "{field.total.required}")
        private BigDecimal total;

        @NotEmptyList(message = "{field.items.required}")
        private List<OrderItemDTO> items;

}