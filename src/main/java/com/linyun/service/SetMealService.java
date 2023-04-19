package com.linyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.dto.SetmealDto;
import com.linyun.pojo.SetMeal;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/02/13:55
 */
public interface SetMealService extends IService<SetMeal> {
    /**
     * 分页查询套餐
     * @param pageTotal 页面启始下标
     * @param pageSize 页面展示条数
     * @param name 模糊查询信息
     * @return
     */
    Rest<PageBean<SetmealDto>> conditionPageSelectByName(Integer pageTotal,Integer pageSize,String name);

    /**
     * 新增套餐信息
     * @param setmealDto
     * @return
     */
    Rest<String> insertMealAll(SetmealDto setmealDto);

    /**
     * 获取修改页面信息
     * @param id
     * @return
     */
    Rest<SetmealDto> updatePageSelectById(Long id);

    /**
     * 修改套餐信息
     * @param setmealDto
     * @return
     */
    Rest<String> updateMealById(SetmealDto setmealDto);

    /**
     * 批量起售或停售套餐
     * @param status 套餐状态 0：停用 1：起售
     * @param ids 需要调整的ids
     * @return
     */
    Rest<String> batchUpdateStatus(Integer status,List<Long> ids);

    /**
     * 批量逻辑删除套餐
     * @param ids
     * @return
     */
    Rest<String> batchDeleteById(List<Long> ids);

    /**
     * 返回套餐关联的信息
     * @param setMeal
     * @return
     */
    Rest<List<SetMeal>> list(SetMeal setMeal);
}
