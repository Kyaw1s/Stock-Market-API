package my.web.stock.market.stockmarket.repositories.jpa;

import my.web.stock.market.stockmarket.repositories.entities.jpa.Asset;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAssetRepository extends JpaRepository<Asset, Long> {
    Asset findByUserAndCurrencyName(User user, String currencyName);
    List<Asset> findByUser(User user);
}
