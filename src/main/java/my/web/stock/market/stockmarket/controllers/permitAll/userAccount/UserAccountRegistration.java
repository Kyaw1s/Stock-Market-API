package my.web.stock.market.stockmarket.controllers.permitAll.userAccount;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.main.UserCredentialDto;
import my.web.stock.market.stockmarket.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAccountRegistration {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@RequestBody UserCredentialDto userCredentialDto) {
        if(userService.tryToSave(userCredentialDto)) {
            return new ResponseEntity<>("User added", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not added", HttpStatus.CONFLICT);
    }
}
