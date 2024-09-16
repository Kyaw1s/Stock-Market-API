package my.web.stock.market.stockmarket.security;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.repositories.jpa.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final UserRepository usersRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usersRepository.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }
}
