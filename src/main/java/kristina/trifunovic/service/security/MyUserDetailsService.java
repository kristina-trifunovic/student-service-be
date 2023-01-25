package kristina.trifunovic.service.security;

import kristina.trifunovic.entity.UserEntity;
import kristina.trifunovic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findById(username);
        user.orElseThrow(() -> new UsernameNotFoundException("user " + username + " does not exist!"));
        return new MyUserDetails(user.get().getUsername(), user.get().getPassword(), user.get().getFirstName(), user.get().getLastName(),
                user.get().getEmail(), user.get().getAddress(), user.get().getCity(), user.get().getAuthorities());
    }
}
