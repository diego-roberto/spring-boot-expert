package io.github.diegoroberto.service;

import io.github.diegoroberto.rest.dto.ClientDTO;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDTO> findById(Long id);

    ClientDTO save(ClientDTO dto);

    void delete(ClientDTO dto);

    List<ClientDTO> findAll(Example<ClientDTO> example);
}
