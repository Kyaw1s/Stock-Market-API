package my.web.stock.market.stockmarket.services;

import my.web.stock.market.stockmarket.dto.currencies.CurrencyExchangeRatesDTO;
import my.web.stock.market.stockmarket.repositories.currencies.CurrenciesExchangeRateRepository;
import my.web.stock.market.stockmarket.repositories.entities.jpa.Asset;
import my.web.stock.market.stockmarket.repositories.jpa.UserAssetRepository;
import my.web.stock.market.stockmarket.services.currencies.CurrenciesConvectorService;
import my.web.stock.market.stockmarket.services.wallet.UserWalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserWalletServiceTest {

    private UserWalletService userWalletService;

    @Mock
    private UserAssetRepository userAssetRepository;
    @Mock
    private CurrenciesExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    public void setUp() {
        userWalletService = new UserWalletService(userAssetRepository, exchangeRateRepository,
                new CurrenciesConvectorService(exchangeRateRepository));
    }

    @Test
    public void getBalance() {
        Asset asset1 = new Asset("USD", 10.0, null);
        Asset asset2 = new Asset("RUB", 1500.0, null);
        Asset asset3 = new Asset("EUR", 12.0, null);

        HashMap<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("RUB", 95.4);
        rates.put("EUR", 1.14);
        CurrencyExchangeRatesDTO currencyExchangeRatesDTO = new CurrencyExchangeRatesDTO(rates);

        Mockito.when(userAssetRepository.findByUser(Mockito.any())).thenReturn(List.of(asset1, asset2, asset3));
        Mockito.when(exchangeRateRepository.getExchangeRates(Mockito.any())).thenReturn(currencyExchangeRatesDTO);

        Assertions.assertEquals(userWalletService.getBalance("USD", null), 36.25);
    }
}
