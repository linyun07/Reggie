package com.linyun.dish;

import com.linyun.dto.DishDto;
import com.linyun.mapper.DishFlavorMapper;
import com.linyun.mapper.DishMapper;
import com.linyun.pojo.Dish;
import com.linyun.pojo.DishFlavor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linyun
 * @date 2023/04/02/17:35
 */
@SpringBootTest
public class dishMapperTest {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Test
    void pageSelectTest() {
        List<DishDto> dishes = dishMapper.pageSelectByCondition(0, 5, null);
        System.out.println(dishes);
    }

    @Test
    void updateSelectById(){
        DishDto dishDto = new DishDto();
        //查询dish对象
        Dish dish = dishMapper.updatePageSelectById(1397851099502260226L);
        //查询口味信息
        List<DishFlavor> dishFlavors = dishFlavorMapper.updatePageSelectById(1397851099502260226L);
        //把dish对象复制到dishDto里面
        BeanUtils.copyProperties(dish, dishDto);
        //设置Flavors口味信息
        dishDto.setFlavors(dishFlavors);
        System.out.println(dishDto.toString());
    }
}
