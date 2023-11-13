package io.github.diegoroberto.rest.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemInfoDTO {
    private String productDescription;
    private BigDecimal priceUnity;
    private Integer quantity;
}
