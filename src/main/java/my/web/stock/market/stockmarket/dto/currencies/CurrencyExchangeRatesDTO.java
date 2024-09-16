package my.web.stock.market.stockmarket.dto.currencies;

import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class CurrencyExchangeRatesDTO {
    public HashMap<String, Double> rates;

    public Double getRate(String currencyName) {
        return rates.get(currencyName);
    }
}
