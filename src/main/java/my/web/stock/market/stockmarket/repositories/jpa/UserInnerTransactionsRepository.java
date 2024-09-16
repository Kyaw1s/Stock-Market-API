package my.web.stock.market.stockmarket.repositories.jpa;


import my.web.stock.market.stockmarket.repositories.entities.jpa.InnerTransaction;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInnerTransactionsRepository extends JpaRepository<InnerTransaction, Long> {
    InnerTransaction findByUserSender(User sender);
}
