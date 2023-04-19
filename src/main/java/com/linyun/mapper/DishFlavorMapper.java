package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author linyun
 * @date 2023/04/01/15:48
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    /**
     * 对于dish页面信息增加口味信息
     * @param dishFlavors
     */
    void insertAll(List<DishFlavor> dishFlavors);

    /**
     * 根据dish页面传回的id修改返回口味信息
     * @param id
     * @return
     */
    @Select("select *from dish_flavor where dish_id=#{id}")
    List<DishFlavor> updatePageSelectById(Long id);

    /**
     * 根据ids逻辑删除dish菜品对应的口味信息
     * @param ids
     */
    void  batchDeleteById( List<Long> ids);
}
