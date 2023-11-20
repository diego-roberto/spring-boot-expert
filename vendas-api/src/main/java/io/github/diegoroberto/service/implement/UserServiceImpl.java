package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.config.internationalization.InternationalizationConfig;
import io.github.diegoroberto.constant.RoleConstants;
import io.github.diegoroberto.domain.entity.User;
import io.github.diegoroberto.domain.repository.UserRepository;
import io.github.diegoroberto.exception.BusinessException;
import io.github.diegoroberto.exception.InvalidPasswordException;
import io.github.diegoroberto.rest.dto.LoginDTO;
import io.github.diegoroberto.rest.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder encoder;

    private final InternationalizationConfig i18n;

    @Transactional
    public UserDTO save(UserDTO dto) {
        User user;
        user = User.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .admin(dto.isAdmin())
                .build();

        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                                  .orElseThrow(() -> new UsernameNotFoundException(i18n.getMessage("msg.auth.login-not-found")));

        String[] roles = user.isAdmin() ?
                new String[]{RoleConstants.ADMIN, RoleConstants.USER} : new String[]{RoleConstants.USER};

                /* não é possível importar classes com mesmo nome, já que a entidade se chama User */
        return org.springframework.security.core.userdetails.User
                .builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .roles(roles)
                .build();
    }

    public UserDetails authenticate(LoginDTO dto) throws InvalidPasswordException{
        UserDetails loadedUser = loadUserByUsername(dto.getLogin());
        boolean match = encoder.matches(dto.getPassword(), loadedUser.getPassword());
        if(match){
            return loadedUser;
        }
        throw new InvalidPasswordException(i18n.getMessage("msg.auth.invalid-password"));
    }

    public List<UserDTO> findAll(){
        List<User> users = userRepository.findAll();

        if (!users.isEmpty()) {
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new BusinessException(i18n.getMessage("msg.user.not-found"));
        }
    }

}
