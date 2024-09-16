package my.web.stock.market.stockmarket.controllers.permitAll.currencies;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.currencies.CurrencyExchangeRatesDTO;
import my.web.stock.market.stockmarket.repositories.currencies.CurrenciesExchangeRateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrenciesExchangerController {
    private final CurrenciesExchangeRateRepository exchangeRateRepository;

    @GetMapping("/currenciesCalculator")
    public Double getCurrencyExchangeAmount(@RequestParam final String from, @RequestParam final String to, @RequestParam(required = false) Double amount) {

        var currencyExchangeRates = exchangeRateRepository.getExchangeRates(from);
        var exchangeRate = currencyExchangeRates.getRate(to);

        if(amount == null) {
            amount = 1.0;
        }

        return Math.round(exchangeRate * amount * 100) / 100.0;
    }

    @GetMapping("/exchangeRates/{currencyName}")
    public CurrencyExchangeRatesDTO getCurrencyExchangeRates(@PathVariable final String currencyName) {
        return exchangeRateRepository.getExchangeRates(currencyName);
    }

}
