package my.web.stock.market.stockmarket.services.richnessChart;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.currencies.CurrencyExchangeRatesDTO;
import my.web.stock.market.stockmarket.dto.user.redis.UserBalanceDTO;
import my.web.stock.market.stockmarket.repositories.currencies.CurrenciesExchangeRateRepository;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.entities.redis.UsersRichnessChart;
import my.web.stock.market.stockmarket.repositories.jpa.UserRepository;
import my.web.stock.market.stockmarket.services.wallet.UserWalletService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UsersRichnessChartService {
    private final RedisTemplate<String, UsersRichnessChart> redisTemplate;
    private final UserRepository userRepository;
    private final UserWalletService userWalletService;
    private final CurrenciesExchangeRateRepository currenciesExchangeRateRepository;
    private final String CHART_NAME = "usersBalanceChartIn%s";

    private final long USER_BALANCE_CHART_TIME_LIFE_IN_SECONDS = 15;

    @Transactional
    public UsersRichnessChart getChart(String currencyName) {
        var valuesOperations = redisTemplate.opsForValue();
        UsersRichnessChart usersRichnessChart = valuesOperations.get(getValueName(currencyName));

        if(usersRichnessChart != null) {
            return usersRichnessChart;
        }

        return createChart(currencyName);
    }

    private UsersRichnessChart createChart(String currencyName) {
        List<User> users = userRepository.findAll();

        UsersRichnessChart usersRichnessChart = new UsersRichnessChart();
        CurrencyExchangeRatesDTO currencyExchangeRatesDTO = currenciesExchangeRateRepository.getExchangeRates(currencyName);

        for (User user : users) {
            double balance = userWalletService.getBalance(user, currencyExchangeRatesDTO);
            usersRichnessChart.addUserBalance(new UserBalanceDTO(user.getEmail(), balance, currencyName));
        }

        usersRichnessChart.sort();

        redisTemplate.opsForValue().set(getValueName(currencyName), usersRichnessChart, USER_BALANCE_CHART_TIME_LIFE_IN_SECONDS, TimeUnit.SECONDS);
        return usersRichnessChart;
    }

    private String getValueName(String currencyName) {
        return String.format(CHART_NAME, currencyName);
    }
}
