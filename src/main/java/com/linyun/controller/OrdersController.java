package com.linyun.controller;

import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.pojo.Orders;
import com.linyun.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author linyun
 * @date 2023/04/05/14:36
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Resource
    private OrdersService ordersService;


    @GetMapping("/page")
    public Rest<PageBean<Orders>> pageSelect(Integer page, Integer pageSize,String number) {
        return ordersService.pageSelect(page, pageSize,number);
    }

    @GetMapping("/userPage")
    public Rest<PageBean<Orders>> userPageSelect(Integer page, Integer pageSize) {
        return ordersService.pageSelect(page, pageSize,null);
    }

    @PostMapping("/submit")
    public Rest<String> submit(@RequestBody Orders orders) {
        return ordersService.submit(orders);
    }

    @PutMapping
    public Rest<String> updateStatus(@RequestBody Orders orders) {
        return ordersService.updateStatus(orders);
    }
}
