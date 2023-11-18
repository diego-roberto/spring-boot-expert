package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.rest.dto.ClientDTO;
import io.github.diegoroberto.service.implement.ClientServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
@Api("API Clientes")
public class ClientController {

    @AutoConfigureOrder
    private final ClientServiceImpl clientService;


    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    public ClientDTO getClienteById(
            @PathVariable
            @ApiParam("Id do cliente") Long id){
        return clientService
                .findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @ApiOperation("Buscar clientes com parâmetros")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    public List<ClientDTO> find(ClientDTO params){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example<ClientDTO> example = Example.of(params, matcher);
        return clientService.findAll(example);
    }

    @GetMapping("/all")
    @ApiOperation("Busca todos os clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    public List<ClientDTO> findAll(){
        return clientService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 400, message = ""),
            @ApiResponse(code = 401, message = "")
    })
    public ClientDTO save(@RequestBody @Valid ClientDTO dto){
        return clientService.save(dto);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 400, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    public void update( @PathVariable Long id,
                        @RequestBody @Valid ClientDTO dto){
        clientService
                .findById(id)
                .map( optional -> {
                    dto.setId(optional.getId());
                    clientService.save(dto);
                    return optional;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND) );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Excluir um cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = ""),
            @ApiResponse(code = 400, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    public void delete( @PathVariable Long id){
        clientService.findById(id)
                .map( client -> {
                    clientService.delete(client);
                    return client;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}
