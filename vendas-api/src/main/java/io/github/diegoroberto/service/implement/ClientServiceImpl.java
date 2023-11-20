package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.config.internationalization.InternationalizationConfig;
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

    private final InternationalizationConfig i18n;

    @Override
    public Optional<ClientDTO> findById(Long id) {
        Optional<Client> optional = clientRepository.findById(id);

        if (optional.isPresent()) {
            ClientDTO clientDTO = modelMapper.map(optional.get(), ClientDTO.class);
            return Optional.ofNullable(clientDTO);
        } else {
            throw new BusinessException(i18n.getMessage("msg.client.not-found"));
        }
    }

    @Override
    public List<ClientDTO> findAll(){
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
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
            throw new IllegalArgumentException(i18n.getMessage("msg.validation.null"));
        }

        Example<Client> exampleEntity = Example.of(modelMapper.map(example, Client.class));
        List<Client> clients = clientRepository.findAll(exampleEntity);

        if (clients.isEmpty()) {
            throw new NoResultsException(i18n.getMessage("msg.validation.not-found"));
        }
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

}
