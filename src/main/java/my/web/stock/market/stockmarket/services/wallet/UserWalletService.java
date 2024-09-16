package my.web.stock.market.stockmarket.services.wallet;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.currencies.CurrencyExchangeRatesDTO;
import my.web.stock.market.stockmarket.repositories.currencies.CurrenciesExchangeRateRepository;
import my.web.stock.market.stockmarket.repositories.entities.jpa.Asset;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserAssetRepository;
import my.web.stock.market.stockmarket.services.currencies.CurrenciesConvectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserWalletService {
    private final UserAssetRepository userAssetRepository;
    private final CurrenciesExchangeRateRepository currenciesExchangeRateRepository;
    private final CurrenciesConvectorService currenciesConvectorService;

    public Double getBalance(String currencyName, User user) {
        CurrencyExchangeRatesDTO currencyExchangeRatesDTO = currenciesExchangeRateRepository.getExchangeRates(currencyName);
        return getBalance(user, currencyExchangeRatesDTO);
    }

    public Double getBalance(User user, CurrencyExchangeRatesDTO currencyExchangeRatesDTO) {
        List<Asset> assets = userAssetRepository.findByUser(user);

        Double balance = 0.0;
        for (Asset asset : assets) {
            Double amount = currenciesConvectorService.convertCurrency(asset.getCurrencyName(), asset.getAmount(), currencyExchangeRatesDTO);
            balance += amount;
        }

        return balance;
    }

}
