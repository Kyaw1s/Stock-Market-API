package my.web.stock.market.stockmarket.services.wallet;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.assets.AssetDTO;
import my.web.stock.market.stockmarket.dto.user.assets.AssetTransferDTO;
import my.web.stock.market.stockmarket.repositories.entities.jpa.Asset;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserAssetRepository;
import my.web.stock.market.stockmarket.repositories.jpa.UserRepository;
import my.web.stock.market.stockmarket.services.currencies.CurrenciesConvectorService;
import my.web.stock.market.stockmarket.services.transactions.UserInnerTransactionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAssetsService {
    private final UserAssetRepository userAssetRepository;
    private final UserRepository userRepository;
    private final UserInnerTransactionsService userInnerTransactionsService;
    private final CurrenciesConvectorService currenciesConvectorService;

    @Transactional
    public void buyAsset(String currencyName, Double amount, User user) {
        Asset asset = userAssetRepository.findByUserAndCurrencyName(user, currencyName);
        if (asset == null) {
            asset = new Asset(currencyName, amount, user);
            userAssetRepository.save(asset);
        } else {
            asset.increaseAmount(amount);
        }
    }

    @Transactional
    public boolean isEnough(String currencyName, Double amount, User user) {
        Asset asset = userAssetRepository.findByUserAndCurrencyName(user, currencyName);
        return asset.isEnough(amount);
    }

    @Transactional
    public boolean tryToSend(AssetTransferDTO assetTransferDTO, User userSender) {
        User userRecipient = userRepository.findUserByEmail(assetTransferDTO.getRecipientEmail());
        Asset senderUserAsset = userAssetRepository.findByUserAndCurrencyName(userSender, assetTransferDTO.getCurrencyName());

        if (userRecipient == null || senderUserAsset == null) {
            return false;
        }

        if(tryToSellAsset(assetTransferDTO.getCurrencyName(), assetTransferDTO.getAmount(), userSender)) {
            buyAsset(assetTransferDTO.getCurrencyName(), assetTransferDTO.getAmount(), userRecipient);
            userInnerTransactionsService.add(userSender, userRecipient, assetTransferDTO);
            return true;
        }

        return false;
    }

    @Transactional
    public List<AssetDTO> getAllAssets(User user) {
        var assets = userAssetRepository.findByUser(user);
        List<AssetDTO> assetDTOS = new ArrayList<>(assets.size());

        for (Asset asset : assets) {
            assetDTOS.add(new AssetDTO(asset.getCurrencyName(), asset.getAmount()));
        }

        return assetDTOS;
    }

    @Transactional
    public boolean tryToSellAsset(String currencyName, Double amount, User user) {
        Asset asset = userAssetRepository.findByUserAndCurrencyName(user, currencyName);

        if (asset != null && asset.isEnough(amount)) {
            asset.decreaseAmount(amount);

            if(asset.getAmount() == 0) {
                userAssetRepository.delete(asset);
            }

            return true;
        }

        return false;
    }

    @Transactional
    public boolean tryToConvert(String currencyNameFrom, String currencyNameTo, Double amount, User user) {
        if(tryToSellAsset(currencyNameFrom, amount, user)) {
            var currencyToAmount = currenciesConvectorService.convertCurrency(currencyNameFrom, currencyNameTo, amount);
            buyAsset(currencyNameTo, currencyToAmount, user);
            return true;
        }
        return false;
    }
}
