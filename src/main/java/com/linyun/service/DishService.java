package com.linyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.dto.DishDto;
import com.linyun.pojo.Dish;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/02/13:54
 */
public interface DishService extends IService<Dish> {
    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    Rest<String> saveWithFlavor(DishDto dishDto);

    /**
     * 菜品的分页查询
     * @param pageTotal
     * @param pageSize
     * @param name
     * @return
     */

    Rest<PageBean<DishDto>> pageSelectCondition(Integer pageTotal,Integer pageSize,String name);

    /**
     * 获取需要修改的菜品信息
     * @param id
     * @return
     */
    Rest<DishDto> updatePageSelectById(Long id);

    /**
     * 修改彩屏信息
     * @param dishDto 修改所需要的信息
     * @return
     */
    Rest<String> updateWithFlavor(DishDto dishDto);

    /**
     * 批量起售或停售菜品
     * @param status
     * @param ids
     * @return
     */
    Rest<String> batchUpdateStatusById(Integer status,List<String> ids);

    /**
     * 批量逻辑删除菜品
     * @param ids
     * @return
     */
    Rest<String> batchDeleteById(List<Long> ids);

    /**
     * 返回菜品
     * @param dish
     * @return
     */
    Rest<List<DishDto>> selectListByCategoryId(Dish dish);
}
