package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.dto.DishDto;
import com.linyun.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    /**
     * 查询对应分类绑定的菜品
     * @param ids
     * @return
     */
    @Select("select count(*) from dish where category_id=#{ids} ")
    Integer selectCountByCategoryId(Long ids);

    /**
     *根据id查询修改页面需要的信息
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id};")
    Dish updatePageSelectById(Long id);

    /**
     *
     * @param dish
     * @return
     */
    @Select("select *from dish where category_id=#{categoryId} and status=1 order by create_time")
    List<Dish> selectListByCategoryId(Dish dish);

    /**
     * 查询满足条件的数据总数
     * @param name
     * @return
     */
    Integer conditionGetCount(String name);

    /**
     * 对菜品页面的分页查询
     * @param pageTotal
     * @param pageSize
     * @param name
     * @return
     */
    List<DishDto> pageSelectByCondition(@Param("pageTotal") Integer pageTotal, @Param("pageSize") Integer pageSize, @Param("name") String name);

    /**
     * 修改菜品信息
     * @param dishDto
     */
    void updateDish(DishDto dishDto);

    /**
     * 新增菜品
     * @param dishDto
     */
    void insertAll(DishDto dishDto);

    /**
     * 修改页面起售或则停售
     * @param status
     * @param ids
     */
    void batchUpdateStatusById(@Param("status") Integer status, @Param("list") List<String> ids);

    /**
     * 逻辑删除页面信息
     * @param ids
     */
    void batchDeleteById(List<Long> ids);
}
