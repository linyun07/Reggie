package com.linyun.service.impl;

import com.linyun.common.BaseContext;
import com.linyun.common.Rest;
import com.linyun.mapper.ShoppingCartMapper;
import com.linyun.pojo.ShoppingCart;
import com.linyun.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author linyun
 * @date 2023/04/05/10:23
 */
@Slf4j
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public Rest<ShoppingCart> addCart(ShoppingCart shoppingCart) {
        //获取用户id 并添加到shoppingCart中
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        //判断菜品或套餐是否已存在
        ShoppingCart cart = shoppingCartMapper.trendsSelectCartDishOrMeal(shoppingCart);
        if (cart != null) {
            //已存在 在原来的数量基础上加一
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberByDishIdOrSetmealId(cart);
        } else {
            //不存在，新增到购物车 数量默认是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.trendsAddShoppingCart(shoppingCart);
            cart = shoppingCart;
        }

        return Rest.success(cart);
    }

    @Override
    public Rest<ShoppingCart> subCart(ShoppingCart shoppingCart) {
        //设置用户id
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //查询改用户的购物车信息
        ShoppingCart cart = shoppingCartMapper.trendsSelectCartDishOrMeal(shoppingCart);
        //判断用户购物车里面商品的数量
        if (cart.getNumber() == 1) {
            //数量为一删除掉该记录
            log.error("购物车开始删除");
            cart.setNumber(0);
            shoppingCartMapper.deleteCartDishOrMeal(shoppingCart);
        } else {
            //商品不唯一就对数量减一
            log.error("购物车开始减少");
            shoppingCart.setNumber(cart.getNumber() - 1);
            shoppingCartMapper.updateNumberByDishIdOrSetmealId(shoppingCart);
            cart = shoppingCart;
        }
        return Rest.success(cart);
    }

    @Override
    public Rest<String> cleanCart() {
        shoppingCartMapper.cleanCart(BaseContext.getCurrentId());
        return Rest.success("清空成功");
    }

    @Override
    public Rest<List<ShoppingCart>> selectCartList() {
        //获取用户id 并添加到shoppingCart中
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectCartList(shoppingCart);
        return Rest.success(shoppingCarts);
    }
}
