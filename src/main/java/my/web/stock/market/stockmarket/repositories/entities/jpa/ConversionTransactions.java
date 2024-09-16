package my.web.stock.market.stockmarket.repositories.entities.jpa;

import jakarta.persistence.*;
import lombok.Data;
import my.web.stock.market.stockmarket.dto.user.assets.AssetConvertDTO;

@Entity
@Table(name = "my_conversion_transactions")
@Data
public class ConversionTransactions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String toCurrencyName;
    private String fromCurrencyName;
    private Double fromCurrencyAmount;
    @ManyToOne
    private User user;

    public ConversionTransactions(User user, AssetConvertDTO convertDTO) {
        this.user = user;
        this.toCurrencyName = convertDTO.getCurrencyNameTo();
        this.fromCurrencyName = convertDTO.getCurrencyNameFrom();
        this.fromCurrencyAmount = convertDTO.getCurrencyFromAmount();
    }

    public ConversionTransactions() {}
}
