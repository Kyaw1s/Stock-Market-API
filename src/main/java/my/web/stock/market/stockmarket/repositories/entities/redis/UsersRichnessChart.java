package my.web.stock.market.stockmarket.repositories.entities.redis;

import lombok.Data;
import my.web.stock.market.stockmarket.dto.user.redis.UserBalanceDTO;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@RedisHash(timeToLive = 15L)
public class UsersRichnessChart implements Serializable {
    private int id;
    private List<UserBalanceDTO> chart = new ArrayList<>();

    public void addUserBalance(UserBalanceDTO userBalanceDTO) {
        chart.add(userBalanceDTO);
    }

    public void sort() {
        Collections.sort(chart);
        Collections.reverse(chart);
    }
}
