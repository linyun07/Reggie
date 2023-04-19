package com.linyun.controller;

import com.linyun.common.Rest;
import com.linyun.pojo.ShoppingCart;
import com.linyun.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linyun
 * @date 2023/04/05/10:23
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 查询显示购物车信息
     *
     * @return
     */
    @GetMapping("/list")
    public Rest<List<ShoppingCart>> selectCartList() {
        return shoppingCartService.selectCartList();
    }

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public Rest<ShoppingCart> addCart(@RequestBody ShoppingCart shoppingCart) {
        log.info("需要添加到购物车的数据有：{}", shoppingCart.toString());
        return shoppingCartService.addCart(shoppingCart);
    }

    /**
     * 减少购物车信息
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public Rest<ShoppingCart> subCart(@RequestBody ShoppingCart shoppingCart) {
        log.error("购物车开始减少");
        return shoppingCartService.subCart(shoppingCart);

    }

    @DeleteMapping("/clean")
    public Rest<String> cleanCart(){
        return shoppingCartService.cleanCart();
    }
}
