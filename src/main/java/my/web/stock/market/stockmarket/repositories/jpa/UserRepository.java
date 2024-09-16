package my.web.stock.market.stockmarket.repositories.jpa;

import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
