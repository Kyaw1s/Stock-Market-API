package my.web.stock.market.stockmarket.repositories.entities.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "my_users_verifications_codes")
public class UserVerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    public UserVerificationCode() {}

    public UserVerificationCode(User user, String code) {
        this.user = user;
        this.code = code;
        this.sentDate = new Date(System.currentTimeMillis());
    }
}
