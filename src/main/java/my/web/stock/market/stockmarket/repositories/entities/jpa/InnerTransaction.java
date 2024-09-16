package my.web.stock.market.stockmarket.repositories.entities.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "my_users_inner_transactions")
@Data
public class InnerTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public InnerTransaction() {}

    public InnerTransaction(User sender, User receiver, String currencyName, double amount) {
        this.userSender = sender;
        this.userRecipient = receiver;
        this.currencyName = currencyName;
        this.amount = amount;
    }

    @ManyToOne
    private User userSender;
    @ManyToOne
    private User userRecipient;

    private String currencyName;
    private double amount;
}
