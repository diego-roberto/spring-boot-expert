package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.rest.dto.UserDTO;
import io.github.diegoroberto.service.implement.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    private final PasswordEncoder encoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "{msg.user.success}"),
            @ApiResponse(code = 400, message = "{msg.validation.error}")
    })
    public UserDTO save(@RequestBody @Valid UserDTO user){
        String encrypted = encoder.encode(user.getPassword());
        user.setPassword(encrypted);
        return userService.save(user);
    }

}
