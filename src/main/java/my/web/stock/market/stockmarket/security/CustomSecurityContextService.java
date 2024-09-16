package my.web.stock.market.stockmarket.security;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomSecurityContextService {
    private final UserRepository userRepository;

    public String getAuthenticationUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getAuthenticationUser() {
        return userRepository.findUserByEmail(getAuthenticationUserEmail());
    }
}
