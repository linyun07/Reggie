package com.linyun.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyun.common.BaseContext;
import com.linyun.common.CustomException;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.mapper.*;
import com.linyun.pojo.*;
import com.linyun.service.OrderDetailService;
import com.linyun.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author linyun
 * @date 2023/04/05/14:35
 */
@Slf4j
@Service
@Transactional
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements OrdersService {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private AddressBookMapper addressBookMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Rest<String> submit(Orders orders) {
        //获取当前用户id
        Long currentId = BaseContext.getCurrentId();
        //查询当前用户的购物车数据
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(currentId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectCartList(cart);
        //查询用户信息
        User user = userMapper.selectUserById(currentId);
        //查询地址信息
        AddressBook addressBook = addressBookMapper.selectAddressBookById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new CustomException("你的地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> ordersDetail = getOrdersDetail(shoppingCarts, orderId, amount);
        log.error("订单明细表信息：{}", ordersDetail);
        //设置订单表信息
        getOrders(orders, currentId, user, addressBook, orderId, amount);
        log.error("订单表信息：{}", orders);
        //设置订单明细信息

        //向订单表插入数据，插入一条数据
        this.save(orders);
        //向订单明细小插入多条数据
        orderDetailService.saveBatch(ordersDetail);
        //清空购物车
        shoppingCartMapper.cleanCart(currentId);
        return Rest.success("支付成功");
    }

    @Override
    public Rest<String> updateStatus(Orders orders) {
        ordersMapper.updateStatus(orders);
        return Rest.success("修改状态成功");
    }

    @Override
    public Rest<PageBean<Orders>> pageSelect(Integer pageTotal, Integer pageSize,String number) {
        pageTotal=(pageTotal-1)*pageSize;
        if (number!=null && number!=""){
            number="%"+number+"%";
        }
        List<Orders> orders = ordersMapper.pageSelect(pageTotal, pageSize,number);
        Integer count = ordersMapper.getCount(number);
        if (pageTotal>count){
            orders=ordersMapper.pageSelect(count-pageSize, pageSize,number);
        }
        return Rest.success(new PageBean<>(orders,count));
    }

    private static List<OrderDetail> getOrdersDetail(List<ShoppingCart> shoppingCarts, long orderId, AtomicInteger amount) {
        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        return orderDetails;
    }

    private static void getOrders(Orders orders, Long currentId, User user, AddressBook addressBook, long orderId, AtomicInteger amount) {
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(currentId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
    }


}
