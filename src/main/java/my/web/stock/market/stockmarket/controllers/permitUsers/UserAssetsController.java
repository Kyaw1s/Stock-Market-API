package my.web.stock.market.stockmarket.controllers.permitUsers;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.assets.AssetDTO;
import my.web.stock.market.stockmarket.dto.user.assets.AssetConvertDTO;
import my.web.stock.market.stockmarket.dto.user.assets.AssetTransferDTO;
import my.web.stock.market.stockmarket.enums.ExternalTransactionType;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.security.CustomSecurityContextService;
import my.web.stock.market.stockmarket.services.wallet.UserAssetsService;
import my.web.stock.market.stockmarket.services.transactions.UserConversionTransactionsService;
import my.web.stock.market.stockmarket.services.transactions.UserExternalTransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user/wallet/assets")
@RequiredArgsConstructor
public class UserAssetsController {
    private final CustomSecurityContextService customSecurityContextService;
    private final UserAssetsService userAssetsService;
    private final UserConversionTransactionsService userConversionTransactionsService;
    private final UserExternalTransactionsService userExternalTransactionsService;

    private final String ASSET_NOT_FOUND_OR_BALANCE_IS_NOT_ENOUGH = "Asset not founded or the asset balance is not enough";

    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        var user = customSecurityContextService.getAuthenticationUser();
        var assets = userAssetsService.getAllAssets(user);

        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/isEnough")
    public boolean isEnough(@RequestParam String currencyName, @RequestParam double amount) {
        var user = customSecurityContextService.getAuthenticationUser();
        return userAssetsService.isEnough(currencyName, amount, user);
    }

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody AssetConvertDTO assetConvertDTO) {
        var user = customSecurityContextService.getAuthenticationUser();

        if(userAssetsService.tryToConvert(assetConvertDTO.getCurrencyNameFrom(), assetConvertDTO.getCurrencyNameTo(),
                assetConvertDTO.getCurrencyFromAmount(), user)) {

            userConversionTransactionsService.add(user, assetConvertDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Conversion successful");
        }
        return new ResponseEntity<>(ASSET_NOT_FOUND_OR_BALANCE_IS_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sell(@RequestBody AssetDTO assetDTO) {
        var user = customSecurityContextService.getAuthenticationUser();

        if(userAssetsService.tryToSellAsset(assetDTO.getCurrencyName(), assetDTO.getAmount(), user)) {
            userExternalTransactionsService.add(user, assetDTO, ExternalTransactionType.SELL);
            return new ResponseEntity<>("Asset sold", HttpStatus.OK);
        }
        return new ResponseEntity<>(ASSET_NOT_FOUND_OR_BALANCE_IS_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestBody AssetDTO assetDTO) {
        var user = customSecurityContextService.getAuthenticationUser();
        userAssetsService.buyAsset(assetDTO.getCurrencyName(), assetDTO.getAmount(), user);
        userExternalTransactionsService.add(user, assetDTO, ExternalTransactionType.BUY);
        return new ResponseEntity<>("Asset bought successfully", HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody AssetTransferDTO assetTransferDTO) {
        User sender = customSecurityContextService.getAuthenticationUser();
        if(userAssetsService.tryToSend(assetTransferDTO, sender)) {
            return new ResponseEntity<>("Asset sent successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no user with this email address or you do not have enough of this currency: "
                + assetTransferDTO.getCurrencyName(), HttpStatus.BAD_REQUEST);
    }
}
