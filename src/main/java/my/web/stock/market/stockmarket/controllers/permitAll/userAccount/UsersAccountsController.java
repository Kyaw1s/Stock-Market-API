package my.web.stock.market.stockmarket.controllers.permitAll.userAccount;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.security.JWTAuthenticationDTO;
import my.web.stock.market.stockmarket.dto.security.RefreshTokenDto;
import my.web.stock.market.stockmarket.dto.user.main.UserCredentialDto;
import my.web.stock.market.stockmarket.services.mail.MailService;
import my.web.stock.market.stockmarket.dto.user.verification.UserVerificationDTO;
import my.web.stock.market.stockmarket.services.user.UserService;
import my.web.stock.market.stockmarket.services.verification.UserVerificationCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsersAccountsController {
    private final UserService userService;
    private final MailService mailService;
    private final UserVerificationCodeService userVerificationCodeService;

    @PostMapping("/sign-in")
    public ResponseEntity<String> singIn(@RequestBody UserCredentialDto userCredentialDto) {
        if(userService.userExist(userCredentialDto)) {
            String code = userVerificationCodeService.generateVerifyCodeForUser();
            userVerificationCodeService.saveVerifyCodeForUser(userCredentialDto, code);
            mailService.sendVerificationCode(userCredentialDto.getEmail(), code);

            return ResponseEntity.ok(String.format("The confirmation code has been sent to your email. You have %d minutes..", userVerificationCodeService.TIME_OUT_FOR_VERIFICATION_IN_MINUTES));
        }

        return new ResponseEntity<>("Invalid sign-in", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/verify")
    public ResponseEntity<JWTAuthenticationDTO> verify(@RequestBody UserVerificationDTO userVerificationDTO) throws AuthenticationException {
        UserCredentialDto userCredentialDto = new UserCredentialDto(userVerificationDTO.getEmail(), userVerificationDTO.getPassword());

        if (userService.userExist(userCredentialDto)
                && userVerificationCodeService.checkVerificationCode(userVerificationDTO)) {
            JWTAuthenticationDTO authenticationDTO = userService.signIn(userCredentialDto);
            return ResponseEntity.ok(authenticationDTO);
        }

        throw new AuthenticationException("Invalid verification");
    }

    @PostMapping("/refresh")
    public JWTAuthenticationDTO refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }


}
