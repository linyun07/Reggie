package com.linyun.ShoppingCart;

import com.linyun.mapper.ShoppingCartMapper;
import com.linyun.pojo.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author linyun
 * @date 2023/04/05/10:37
 */
@SpringBootTest
public class ShoppingCartMapperTest {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    public void insertTest() {
        ShoppingCart cart = new ShoppingCart();
        cart.setImage("nadifosh");
        cart.setName("三十五");
        cart.setUserId(35L);
        cart.setNumber(2);
        cart.setAmount(BigDecimal.valueOf(35));
        shoppingCartMapper.trendsAddShoppingCart(cart);
    }

}
