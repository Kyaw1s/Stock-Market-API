package my.web.stock.market.stockmarket.services.transactions;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.assets.AssetDTO;
import my.web.stock.market.stockmarket.enums.ExternalTransactionType;
import my.web.stock.market.stockmarket.repositories.entities.jpa.ExternalTransaction;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserExternalTransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserExternalTransactionsService {
    private final UserExternalTransactionsRepository userExternalTransactionsRepository;

    @Transactional
    public void add(User user, AssetDTO assetDTO, ExternalTransactionType externalTransactionType) {
        ExternalTransaction externalTransaction = new ExternalTransaction(user, assetDTO, externalTransactionType);
        userExternalTransactionsRepository.save(externalTransaction);
    }
}
