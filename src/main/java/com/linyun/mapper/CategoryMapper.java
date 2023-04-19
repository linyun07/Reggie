package com.linyun.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 菜品及套餐分类(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-29 19:19:03
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询分类总数 用于分页查询
     * @return
     */
    @Select("select  count(*) from category")
    Integer conditionGetCount();

    /**
     * 分页查询
     * @param pageTotal
     * @param pageSize
     * @return
     */
    @Select("select * from category  order by create_time limit #{pageTotal},#{pageSize};")
    List<Category> pageSelect(Integer pageTotal, Integer pageSize);

    /**
     * 查询菜品或套餐分类 返回给meal和dish
     * @param category
     * @return
     */
    @Select("SELECT *from category where type=#{type} order by update_time")
    List<Category> dishSelectType(Category category);

    /**
     * 新增菜品或套餐
     * @param category
     * @return
     */
    @Insert("insert into category values(null,#{type},#{name},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    Integer insertCategory(Category category);

    /**
     * 修改菜品或套餐
     * @param category
     * @return
     */
    @Update("update category set type=#{type},name=#{name},sort=#{sort},update_time=#{updateTime},udpate_user=#{updateUser} where id=#{id}")
    Integer updateCategoryById(Category category);

    /**
     * 删除菜品或套餐
     * @param id
     * @return
     */
    @Delete("delete from category where id=#{id};")
    Integer deleteCategoryById(Long id);
}

