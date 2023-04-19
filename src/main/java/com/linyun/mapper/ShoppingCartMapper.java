package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/05/10:22
 */
@Mapper
public interface ShoppingCartMapper  extends BaseMapper<ShoppingCart> {
    /**
     * 动态添加套餐或菜品
     * @param shoppingCart
     */
    void trendsAddShoppingCart(ShoppingCart shoppingCart);

    /**
     * 动态查询套餐或菜品时候存在 或查询用户添加到购物车的菜品套餐
     * @param shoppingCart
     * @return
     */
    ShoppingCart trendsSelectCartDishOrMeal(ShoppingCart shoppingCart);

    /**
     * 更改数量
     * @param shoppingCart
     */
    void updateNumberByDishIdOrSetmealId(ShoppingCart shoppingCart);

    /**
     * 查询用户购物车的记录
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> selectCartList(ShoppingCart shoppingCart);

    /**
     * 删除购物车记录
     * @param shoppingCart
     */
    void deleteCartDishOrMeal(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @param userId
     */
    void cleanCart(Long userId);
}
