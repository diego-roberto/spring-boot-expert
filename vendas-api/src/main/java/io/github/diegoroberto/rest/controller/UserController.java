package io.github.diegoroberto.rest.controller;

import io.github.diegoroberto.constant.MessageConstants;
import io.github.diegoroberto.domain.entity.User;
import io.github.diegoroberto.exception.InvalidPasswordException;
import io.github.diegoroberto.rest.dto.LoginDTO;
import io.github.diegoroberto.rest.dto.TokenDTO;
import io.github.diegoroberto.rest.dto.UserDTO;
import io.github.diegoroberto.security.jwt.JwtService;
import io.github.diegoroberto.service.implement.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo user")
    @ApiResponses({
            @ApiResponse(code = 201, message = MessageConstants.MSG_USER_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR)
    })
    public UserDTO save(@RequestBody @Valid UserDTO user){
        String encrypted = encoder.encode(user.getPassword());
        user.setPassword(encrypted);
        return userService.save(user);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Realizar autenticação")
    @ApiResponses({
            @ApiResponse(code = 200, message = MessageConstants.MSG_USER_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR),
            @ApiResponse(code = 401, message = MessageConstants.MSG_AUTH_LOGIN_NOT_FOUND),
            @ApiResponse(code = 403, message = MessageConstants.MSG_AUTH_INVALID_PASSWORD)
    })
    public TokenDTO authenticate(@RequestBody @Valid LoginDTO dto){
        try {
            UserDetails authUser = userService.authenticate(dto);

            User user = User.builder()
                    .login(authUser.getUsername())
                    .password(authUser.getPassword())
                    .build();

            String token = jwtService.generateToken(user);
            return new TokenDTO(user.getLogin(), token);

        }catch (UsernameNotFoundException | InvalidPasswordException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Obter lista de usuários *debug*")
    @ApiResponses({
            @ApiResponse(code = 200, message = MessageConstants.MSG_USER_SUCCESS),
            @ApiResponse(code = 400, message = MessageConstants.MSG_VALIDATION_ERROR)
    })
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

}
