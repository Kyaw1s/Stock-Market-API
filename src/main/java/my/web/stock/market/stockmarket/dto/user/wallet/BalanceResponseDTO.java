package my.web.stock.market.stockmarket.dto.user.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceResponseDTO {
    private Double balance;
    private String currencyName;
}
