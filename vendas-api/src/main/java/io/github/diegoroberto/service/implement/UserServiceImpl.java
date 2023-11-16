package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.config.InternationalizationConfig;
import io.github.diegoroberto.domain.entity.User;
import io.github.diegoroberto.domain.repository.UserRepository;
import io.github.diegoroberto.rest.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

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
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

                /* não é possível importar classes com mesmo nome, já que a entidade se chama User */
        return org.springframework.security.core.userdetails.User
                .builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .roles(roles)
                .build();
    }

}
