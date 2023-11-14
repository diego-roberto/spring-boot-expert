package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.rest.dto.ClientDTO;
import io.github.diegoroberto.service.implement.ClientServiceImpl;
import io.swagger.annotations.*;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Api("API Clientes")
public class ClientController {

    @AutoConfigureOrder
    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    private static final String NOT_FOUND = "Cliente não encontrado";
    private static final String FOUND = "Cliente encontrado";


    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = FOUND),
            @ApiResponse(code = 404, message = NOT_FOUND + "para o ID informado")
    })
    public ClientDTO getClienteById(
            @PathVariable
            @ApiParam("Id do cliente") Long id ){
        return clientService
                .findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public ClientDTO save(@RequestBody @Valid ClientDTO dto){
        return clientService.save(dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long id){
        clientService.findById(id)
                .map( client -> {
                    clientService.delete(client);
                    return client;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND) );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Long id,
                        @RequestBody @Valid ClientDTO dto){
        clientService
                .findById(id)
                .map( optional -> {
                    dto.setId(optional.getId());
                    clientService.save(dto);
                    return optional;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND) );
    }

    @GetMapping
    public List<ClientDTO> find(ClientDTO params){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example<ClientDTO> example = Example.of(params, matcher);
        return clientService.findAll(example);
    }

}
