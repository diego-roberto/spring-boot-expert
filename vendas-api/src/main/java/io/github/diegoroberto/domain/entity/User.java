package io.github.diegoroberto.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "{field.login.required}")
    private String login;

    @Column
    @NotEmpty(message = "{field.password.required}")
    private String password;

    @Column
    private boolean admin;


}
