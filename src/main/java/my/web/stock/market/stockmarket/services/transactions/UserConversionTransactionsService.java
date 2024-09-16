package my.web.stock.market.stockmarket.services.transactions;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.assets.AssetConvertDTO;
import my.web.stock.market.stockmarket.repositories.entities.jpa.ConversionTransactions;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserConversionTransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserConversionTransactionsService {
    private final UserConversionTransactionsRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void add(User user, AssetConvertDTO assetConvertDTO) {
        ConversionTransactions conversionTransactions = new ConversionTransactions(user, assetConvertDTO);
        repository.save(conversionTransactions);
    }
}
