package my.web.stock.market.stockmarket.repositories.jpa;

import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.entities.jpa.UserVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationCodeRepository extends JpaRepository<UserVerificationCode, Long> {
    UserVerificationCode findByUser(User user);
}
