package my.web.stock.market.stockmarket.repositories.entities.jpa;

import jakarta.persistence.*;
import lombok.Data;
import my.web.stock.market.stockmarket.dto.user.assets.AssetDTO;
import my.web.stock.market.stockmarket.enums.ExternalTransactionType;

@Entity
@Table(name = "my_external_transactions")
@Data
public class ExternalTransaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExternalTransactionType type;
    private String currencyName;
    private Double amount;
    @ManyToOne
    private User user;

    public ExternalTransaction() {}

    public ExternalTransaction(User user, AssetDTO assetDTO, ExternalTransactionType type) {
        this.type = type;
        this.currencyName = assetDTO.getCurrencyName();
        this.amount = assetDTO.getAmount();
        this.user = user;
    }
}
