package io.github.diegoroberto.rest.dto;

import io.github.diegoroberto.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    @NotEmpty(message = "{field.name.required}")
    private String name;

    @NotEmpty(message = "{field.cpf.required}")
    @CPF(message = "{field.cpf.invalid}")
    private String cpf;

    private Set<Order> orders;

}
