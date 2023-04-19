package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.SetMealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐菜品关系表
 *
 * @author linyun
 * @date 2023/04/03/20:17
 */
@Mapper
public interface SetMealDishMapper extends BaseMapper<SetMealDish> {

    /**
     * 根据setMeal传回来的信息 修改菜品信息
     *
     * @param id
     * @return
     */
    @Select("select *from setmeal_dish where setmeal_id=#{id}")
    List<SetMealDish> updateIndexSelectById(Long id);

    /**
     * 逻辑删除菜品
     *
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据setMeal传回来的信息 新增套餐中的菜品信息
     *
     * @param setMealDishes
     */
    void insertMealDish(List<SetMealDish> setMealDishes);

    /**
     * 批量逻辑删除
     *
     * @param setmealId
     */
    void batchDeleteBySetmealId(List<Long> setmealId);
}
