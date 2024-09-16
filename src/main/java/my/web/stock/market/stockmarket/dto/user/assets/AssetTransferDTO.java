package my.web.stock.market.stockmarket.dto.user.assets;

import lombok.Data;

@Data
public class AssetTransferDTO {
    private String recipientEmail;
    private Double amount;
    private String currencyName;
}
