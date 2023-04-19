package com.linyun.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.dto.DishDto;
import com.linyun.pojo.Category;
import com.linyun.pojo.Dish;
import com.linyun.pojo.DishFlavor;
import com.linyun.service.DishFlavorService;
import com.linyun.service.DishService;
import com.linyun.service.categoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author linyun
 * @date 2023/04/02/13:56
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private categoryService categoryService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Rest<PageBean<DishDto>> pageSelect(Integer page, Integer pageSize, String name) {
        return dishService.pageSelectCondition(page, pageSize, name);
    }

    /**
     * 修改页面查询信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Rest<DishDto> updatePageSelectById(@PathVariable Long id) {
        log.info(id.toString());
        return dishService.updatePageSelectById(id);
    }


    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public Rest<String> saveWithFlavor(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        return dishService.saveWithFlavor(dishDto);
    }

    /**
     * 修改起售或者停售
     * @param status 起售或停售状态
     * @param ids 需要修改的ids
     * @return
     */
    @PostMapping("/status/{status}")
    Rest<String> batchUpdateStatusById(@PathVariable Integer status, @RequestParam List<String> ids) {
        log.info("停售是0起售是1：" + status.toString() + " 修改的id有：" + ids.toString());
        return dishService.batchUpdateStatusById(status, ids);
    }

    @PutMapping
    public Rest<String> updateWithFlavor(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        return dishService.updateWithFlavor(dishDto);
    }

    /**
     * 逻辑删除或批量逻辑删除 菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public Rest<String> batchDeleteById(@RequestParam List<Long> ids) {
        log.info(ids.toString());
        return dishService.batchDeleteById(ids);
    }
    /**
     * 查询菜品分类 返回给meal页面
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public Rest<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;
        //拼接key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        //判断如果缓存中有数据直接返回redis里面的数据
        if (dishDtoList != null) {
            return Rest.success(dishDtoList);
        } else {
            //构造查询条件
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
            //添加条件，查询状态为1（起售状态）的菜品
            queryWrapper.eq(Dish::getStatus, 1);

            //添加排序条件
            queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

            List<Dish> list = dishService.list(queryWrapper);

             dishDtoList = list.stream().map((item) -> {
                DishDto dishDto = new DishDto();

                BeanUtils.copyProperties(item, dishDto);

                Long categoryId = item.getCategoryId();//分类id
                //根据id查询分类对象
                Category category = categoryService.getById(categoryId);

                if (category != null) {
                    String categoryName = category.getName();
                    dishDto.setCategoryName(categoryName);
                }

                //当前菜品的id
                Long dishId = item.getId();
                LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
                //SQL:select * from dish_flavor where dish_id = ?
                List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
                dishDto.setFlavors(dishFlavorList);
                return dishDto;
            }).collect(Collectors.toList());
            //数据不存在，将查到的数据缓存到redis中
            redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
            return Rest.success(dishDtoList);
        }
    }
}
