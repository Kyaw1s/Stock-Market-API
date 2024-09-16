package my.web.stock.market.stockmarket.dto.user.redis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserBalanceDTO implements Comparable<UserBalanceDTO>, Serializable {
    private String email;
    private double balance;
    private String currencyName;

    @Override
    public int compareTo(UserBalanceDTO o) {
        if(this.balance > o.balance) {
            return 1;
        } else if(this.balance < o.balance) {
            return -1;
        }
        return 0;
    }
}
