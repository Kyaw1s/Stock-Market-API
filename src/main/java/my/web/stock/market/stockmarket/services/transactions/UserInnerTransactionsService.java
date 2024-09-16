package my.web.stock.market.stockmarket.services.transactions;

import lombok.RequiredArgsConstructor;
import my.web.stock.market.stockmarket.dto.user.assets.AssetTransferDTO;
import my.web.stock.market.stockmarket.repositories.entities.jpa.InnerTransaction;
import my.web.stock.market.stockmarket.repositories.entities.jpa.User;
import my.web.stock.market.stockmarket.repositories.jpa.UserInnerTransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInnerTransactionsService {
    private final UserInnerTransactionsRepository userInnerTransactionsRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void add(User sender, User receiver, AssetTransferDTO assetTransferDTO) {
        var innerTransaction = new InnerTransaction(sender, receiver,
                assetTransferDTO.getCurrencyName(), assetTransferDTO.getAmount());
        userInnerTransactionsRepository.save(innerTransaction);
    }
}
