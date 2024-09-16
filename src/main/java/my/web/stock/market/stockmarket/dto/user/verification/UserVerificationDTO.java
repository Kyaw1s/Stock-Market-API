package my.web.stock.market.stockmarket.dto.user.verification;

import lombok.Data;

@Data
public class UserVerificationDTO {
    private String email;
    private String password;
    private String verificationCode;
}
