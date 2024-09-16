package my.web.stock.market.stockmarket.dto.user.assets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetDTO {
    private String currencyName;
    private Double amount;
}
