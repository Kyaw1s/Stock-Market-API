package my.web.stock.market.stockmarket.repositories.currencies;

import my.web.stock.market.stockmarket.dto.currencies.CurrencyExchangeRatesDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class CurrenciesExchangeRateRepository {

    private final String BASE_URL = "https://api.exchangerate-api.com";
    private final WebClient webClient;

    public CurrenciesExchangeRateRepository() {
        webClient = WebClient.create(BASE_URL);
    }

    public CurrencyExchangeRatesDTO getExchangeRates(String currencyName) {
        return webClient
                .get()
                .uri("/v4/latest/" + currencyName)
                .retrieve()
                .bodyToMono(CurrencyExchangeRatesDTO.class)
                .block();
    }
}
