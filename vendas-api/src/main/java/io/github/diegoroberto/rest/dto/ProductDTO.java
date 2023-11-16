package io.github.diegoroberto.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotEmpty(message = "{field.description.required}")
    private String description;

    @NotNull(message = "{field.price.required}")
    private BigDecimal price;

}
