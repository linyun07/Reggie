package com.linyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.dto.DishDto;
import com.linyun.mapper.DishFlavorMapper;
import com.linyun.mapper.DishMapper;
import com.linyun.pojo.Dish;
import com.linyun.pojo.DishFlavor;
import com.linyun.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author linyun
 * @date 2023/04/02/13:54
 */
@Service
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 对菜品的分页查询
     * @param pageTotal 页面启始下标
     * @param pageSize 页面条数
     * @param name 模糊查询信息
     * @return
     */
    @Override
    public Rest<PageBean<DishDto>> pageSelectCondition(Integer pageTotal, Integer pageSize, String name) {
        pageTotal = (pageTotal - 1) * pageSize;
        if (name != null && name != "") {
            name = "%" + name + "%";
        }
        Integer totalCount = dishMapper.conditionGetCount(name);
        List<DishDto> dishes = dishMapper.pageSelectByCondition(pageTotal, pageSize, name);

        if (pageTotal > totalCount) {
            dishes = dishMapper.pageSelectByCondition(totalCount - pageSize, pageSize, name);
        }
        return Rest.success(new PageBean<>(dishes, totalCount));
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @Override
    public Rest<String> saveWithFlavor(@RequestBody DishDto dishDto) {
        dishMapper.insertAll(dishDto);
        setFlavorsByDishId(dishDto);
        //redis清除缓存
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return Rest.success("添加成功");
    }

    /**
     * 修改菜品信息
     * @param dishDto 修改菜品所需信息
     * @return
     */
    @Override
    public Rest<String> updateWithFlavor(DishDto dishDto) {
        dishMapper.updateDish(dishDto);
        Long dishId = dishDto.getId();
        dishFlavorMapper.deleteById(dishId);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorMapper.insertAll(flavors);
        //redis清除缓存
        String keys="dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(keys);
        return Rest.success("修改成功");
    }

    /**
     * 修改页面
     * @param id 需要修改的id
     * @return
     */
    @Override
    public Rest<DishDto> updatePageSelectById(Long id) {
        DishDto dishDto = new DishDto();
        //查询dish对象
        Dish dish = dishMapper.updatePageSelectById(id);
        //查询口味信息
        List<DishFlavor> dishFlavors = dishFlavorMapper.updatePageSelectById(id);
        //把dish对象复制到dishDto里面
        BeanUtils.copyProperties(dish, dishDto);
        //设置Flavors口味信息
        dishDto.setFlavors(dishFlavors);
        //返回结果
        return Rest.success(dishDto);
    }

    /**
     * 批量起售或停售菜品
     * @param status 判断是停售还是起售
     * @param ids
     * @return
     */
    @Override
    public Rest<String> batchUpdateStatusById(Integer status, List<String> ids) {
        dishMapper.batchUpdateStatusById(status, ids);
        return Rest.success(status == 0 ? "停售成功" : "起售成功");
    }

    /**
     * 批量逻辑删除菜品
     * @param ids
     * @return
     */
    @Override
    public Rest<String> batchDeleteById(List<Long> ids) {
        dishMapper.batchDeleteById(ids);
        dishFlavorMapper.batchDeleteById(ids);
        return Rest.success("删除成功");
    }

    /**
     * 返回菜品信息
     * @param dish
     * @return
     */
    @Override
    public Rest<List<DishDto>> selectListByCategoryId(Dish dish) {
        List<Dish> dishes = dishMapper.selectListByCategoryId(dish);
        return null;
    }

    /**
     * 设置DishFlavor所需要的id
     * @param dishDto
     */
    private void setFlavorsByDishId(DishDto dishDto) {
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorMapper.insertAll(flavors);
    }

}
