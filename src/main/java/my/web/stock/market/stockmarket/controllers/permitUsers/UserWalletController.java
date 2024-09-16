package my.web.stock.market.stockmarket.controllers.permitUsers;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.wallet.BalanceResponseDTO;
import my.web.stock.market.stockmarket.security.CustomSecurityContextService;
import my.web.stock.market.stockmarket.services.wallet.UserWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/wallet")
public class UserWalletController {
    private final UserWalletService userWalletService;
    private final CustomSecurityContextService customSecurityContextService;

    @GetMapping("/balance/{currencyName}")
    public ResponseEntity<BalanceResponseDTO> balance(@PathVariable("currencyName") String currencyName) {
        var user = customSecurityContextService.getAuthenticationUser();
        Double balance = userWalletService.getBalance(currencyName, user);

        return new ResponseEntity<>(new BalanceResponseDTO(balance, currencyName), HttpStatus.OK);
    }
}
