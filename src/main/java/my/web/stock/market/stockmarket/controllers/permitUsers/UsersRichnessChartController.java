package my.web.stock.market.stockmarket.controllers.permitUsers;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.repositories.entities.redis.UsersRichnessChart;
import my.web.stock.market.stockmarket.services.richnessChart.UsersRichnessChartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/rich-chart")
public class UsersRichnessChartController {
    private final UsersRichnessChartService usersRichnessChartService;

    @GetMapping("/{currencyName}")
    public ResponseEntity<UsersRichnessChart> getRichChart(@PathVariable String currencyName) {
        return new ResponseEntity<>(usersRichnessChartService.getChart(currencyName), HttpStatus.OK);
    }
}
