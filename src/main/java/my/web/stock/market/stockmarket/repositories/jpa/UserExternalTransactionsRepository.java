package my.web.stock.market.stockmarket.repositories.jpa;

import my.web.stock.market.stockmarket.repositories.entities.jpa.ExternalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExternalTransactionsRepository extends JpaRepository<ExternalTransaction, Long> {
}
