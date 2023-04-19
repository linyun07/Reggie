package com.linyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.pojo.Orders;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/05/14:35
 */
public interface OrdersService extends IService<Orders> {
    Rest<String> submit(Orders orders);

    Rest<String> updateStatus(Orders orders);

    Rest<PageBean<Orders>> pageSelect(Integer pageTotal,Integer pageSize,String number);
}
