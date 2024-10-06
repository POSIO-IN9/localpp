package edu.pnu.config;

import edu.pnu.domain.Role;
import edu.pnu.domain.UserEntity;
import edu.pnu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        UserEntity user = userOpt.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getUser_type().name())))
                .build();
    }
}
