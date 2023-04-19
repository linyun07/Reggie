package com.linyun.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.dto.SetmealDto;
import com.linyun.pojo.SetMeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/01/15:49
 */
@Mapper
public interface SetMealMapper extends BaseMapper<SetMeal> {
    /**
     * 返回判定套餐的总条数
     *
     * @param ids
     * @return
     */
    @Select("select count(*) from setmeal where category_id=#{ids} ")
    Integer selectCountByCategoryId(Long ids);

    /**
     * 根据id 查询信息 返回给修改页面
     *
     * @param id
     * @return
     */
    @Select("select *from setmeal where id=#{id}")
    SetMeal updateIndexSelectById(Long id);

    @Select("SELECT *from setMeal where category_id = #{categoryId} AND status = #{status} ORDER BY update_time DESC")
    List<SetMeal> selectByCategoryIdAndStatus(SetMeal setMeal);

    /**
     * 对套餐的分页查询
     *
     * @param pageTotal
     * @param pageSize
     * @param name
     * @return
     */
    List<SetmealDto> conditionPageSelectByName(@Param("pageTotal") Integer pageTotal, @Param("pageSize") Integer pageSize, @Param("name") String name);

    /**
     * 根据name条件查询满足信息的总条数
     *
     * @param name
     * @return
     */
    Integer conditionGetCountByName(String name);

    /**
     * 修改套餐信息
     *
     * @param setmealDto
     */
    void updateMealById(SetmealDto setmealDto);

    /**
     * 新增套餐
     *
     * @param setmealDto
     */
    void insertMealAll(SetmealDto setmealDto);

    /**
     * 批量起售或停售套餐
     *
     * @param status 套餐 状态
     * @param ids    需要调整的ids
     */
    void batchUpdateStatus(@Param("status") Integer status, @Param("list") List<Long> ids);

    /**
     * 根据id 逻辑批量删除
     *
     * @param ids
     */
    void batchDeleteById(List<Long> ids);
}
