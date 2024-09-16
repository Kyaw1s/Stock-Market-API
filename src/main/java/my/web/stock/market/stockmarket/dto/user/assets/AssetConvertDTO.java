package my.web.stock.market.stockmarket.dto.user.assets;

import lombok.Data;

@Data
public class AssetConvertDTO {
    private String currencyNameFrom;
    private String currencyNameTo;
    private Double currencyFromAmount;
}
