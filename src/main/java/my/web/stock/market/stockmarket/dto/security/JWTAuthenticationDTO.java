package my.web.stock.market.stockmarket.dto.security;

import lombok.Data;

@Data
public class JWTAuthenticationDTO {
    private String token;
    private String refreshToken;
}
