package my.web.stock.market.stockmarket.dto.user.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentialDto {
    private String email;
    private String password;
}
