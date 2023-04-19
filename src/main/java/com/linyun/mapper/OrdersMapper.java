package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/05/14:34
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {


    List<Orders> pageSelect(@Param("pageTotal") Integer pageTotal, @Param("pageSize") Integer pageSize,
                            @Param("number") String number);


    Integer getCount(String number);


    void updateStatus(Orders orders);
}
