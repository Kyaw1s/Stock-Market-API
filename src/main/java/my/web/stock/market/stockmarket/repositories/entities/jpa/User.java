package my.web.stock.market.stockmarket.repositories.entities.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "my_users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;
    @Column(unique = true, nullable = false)
    private String email;


    public User() {}

    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }

}
