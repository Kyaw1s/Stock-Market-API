package my.web.stock.market.stockmarket.services.user;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.security.JWTAuthenticationDTO;
import my.web.stock.market.stockmarket.dto.security.RefreshTokenDto;
import my.web.stock.market.stockmarket.dto.user.main.UserCredentialDto;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserRepository;
import my.web.stock.market.stockmarket.security.jwt.JWTService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public JWTAuthenticationDTO signIn(UserCredentialDto userCredentialDto) {
        User user = findByCredentials(userCredentialDto);
        if(user == null) {
            return null;
        }
        return jwtService.generateAuthToken(user.getEmail());
    }

    public boolean userExist(UserCredentialDto userCredentialDto) {
        User user = usersRepository.findUserByEmail(userCredentialDto.getEmail());
        return user != null && passwordEncoder.matches(userCredentialDto.getPassword(), user.getPassword());
    }

    public JWTAuthenticationDTO refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Transactional
    public boolean tryToSave(UserCredentialDto UserCredentialDto) {
        if(usersRepository.findUserByEmail(UserCredentialDto.getEmail()) != null) {
            return false;
        }

        User user = new User(UserCredentialDto.getEmail(), passwordEncoder.encode(UserCredentialDto.getPassword()));

        usersRepository.save(user);
        return true;
    }

    private User findByCredentials(UserCredentialDto userCredentialDto) {
        User user = usersRepository.findUserByEmail(userCredentialDto.getEmail());
        if(user != null && passwordEncoder.matches(userCredentialDto.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

    private User findByEmail(String email) throws Exception {
        var user = usersRepository.findUserByEmail(email);
        if(user == null) {
            throw new Exception(String.format("User with email %s not found", email));
        }
        return user;
    }
}
