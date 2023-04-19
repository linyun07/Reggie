package com.linyun.service;

import com.linyun.common.Rest;
import com.linyun.pojo.ShoppingCart;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/05/10:23
 */
public interface ShoppingCartService {
    /**
     * 添加菜品或套餐到购物车
     * @param shoppingCart
     * @return
     */
    Rest<ShoppingCart> addCart(ShoppingCart shoppingCart);

    /**
     * 查询每个单独用户的购物车
     * @return
     */
    Rest<List<ShoppingCart>> selectCartList();

    /**
     * 删除或减少用户的购物车
     * @param shoppingCart
     * @return
     */
    Rest<ShoppingCart> subCart(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @return
     */
    Rest<String> cleanCart();
}
