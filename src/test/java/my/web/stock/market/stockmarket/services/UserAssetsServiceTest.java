package my.web.stock.market.stockmarket.services;


import my.web.stock.market.stockmarket.repositories.entities.jpa.Asset;
import my.web.stock.market.stockmarket.repositories.jpa.UserAssetRepository;
import my.web.stock.market.stockmarket.services.wallet.UserAssetsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserAssetsServiceTest {

    @InjectMocks
    private UserAssetsService userAssetsService;
    @Mock
    private UserAssetRepository userAssetRepository;

    @Test
    public void isEnough() {
        Mockito.when(userAssetRepository.findByUserAndCurrencyName(null, "USD"))
                .thenReturn(new Asset("USD", 10.0, null));

        Assertions.assertTrue(userAssetsService.isEnough("USD", 9.0, null));
        Assertions.assertFalse(userAssetsService.isEnough("USD", 11.0, null));
    }
}
