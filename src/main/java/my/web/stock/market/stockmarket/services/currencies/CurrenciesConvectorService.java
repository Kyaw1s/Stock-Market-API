package my.web.stock.market.stockmarket.services.currencies;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.currencies.CurrencyExchangeRatesDTO;
import my.web.stock.market.stockmarket.repositories.currencies.CurrenciesExchangeRateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrenciesConvectorService {
    private final CurrenciesExchangeRateRepository currenciesExchangeRateRepository;

    public Double convertCurrency(String fromCurrency, String toCurrency, Double fromCurrencyAmount) {
        CurrencyExchangeRatesDTO exchangeRatesDTO = currenciesExchangeRateRepository.getExchangeRates(fromCurrency);
        return Math.round(exchangeRatesDTO.getRate(toCurrency) * fromCurrencyAmount * 100) / 100.0;
    }

    public Double convertCurrency(String fromCurrency, Double fromCurrencyAmount, CurrencyExchangeRatesDTO toCurrencyExchangeRatesDTO) {
        Double exchangeRate = toCurrencyExchangeRatesDTO.getRate(fromCurrency);
        return Math.round(fromCurrencyAmount / exchangeRate * 100) / 100.0;
    }
}
