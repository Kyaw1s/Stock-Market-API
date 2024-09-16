package my.web.stock.market.stockmarket.repositories.entities.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "my_users_assets")
public class Asset {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String currencyName;
    private Double amount;

    @ManyToOne
    private User user;

    public Asset() {}

    public Asset(String currencyName, Double amount, User user) {
        this.currencyName = currencyName;
        this.amount = amount;
        this.user = user;
    }

    public boolean isEnough(Double amount) {
        return this.amount >= amount;
    }

    public void decreaseAmount(Double amount) {
        this.amount -= amount;
    }

    public void increaseAmount(Double amount) {
        this.amount += amount;
    }
}
