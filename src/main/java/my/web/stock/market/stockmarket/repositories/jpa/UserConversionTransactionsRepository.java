package my.web.stock.market.stockmarket.repositories.jpa;

import my.web.stock.market.stockmarket.repositories.entities.jpa.ConversionTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConversionTransactionsRepository extends JpaRepository<ConversionTransactions, Long> {
}
