package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.domain.entity.Client;
import io.github.diegoroberto.domain.repository.ClientRepository;
import io.github.diegoroberto.exception.BusinessException;
import io.github.diegoroberto.exception.NoResultsException;
import io.github.diegoroberto.rest.dto.ClientDTO;
import io.github.diegoroberto.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ModelMapper modelMapper;

    @Override
    public Optional<ClientDTO> findById(Long id) {
        Optional<Client> optional = clientRepository.findById(id);

        if (optional.isPresent()) {
            ClientDTO clientDTO = modelMapper.map(optional.get(), ClientDTO.class);
            return Optional.ofNullable(clientDTO);
        } else {
            throw new BusinessException("Cliente não encontrado.");
        }
    }

    @Override
    @Transactional
    public ClientDTO save(ClientDTO dto){
        Client client = modelMapper.map(dto, Client.class);
        client = clientRepository.save(client);
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public void delete(ClientDTO dto){
        clientRepository.delete(modelMapper.map(dto, Client.class));
    }

    @Override
    public List<ClientDTO> findAll(Example<ClientDTO> example) {
        if (example == null) {
            throw new IllegalArgumentException("O parâmetro não pode ser nulo.");
        }
        List<Client> clients = clientRepository.findAll((Example) example);

        if (clients.isEmpty()) {
            throw new NoResultsException("Nenhum cliente encontrado com o parâmetro fornecido.");
        }
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

}
