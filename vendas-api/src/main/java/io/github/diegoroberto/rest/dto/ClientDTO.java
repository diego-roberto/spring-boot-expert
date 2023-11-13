package io.github.diegoroberto.rest.dto;

import io.github.diegoroberto.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    private Set<Order> orders;

}
