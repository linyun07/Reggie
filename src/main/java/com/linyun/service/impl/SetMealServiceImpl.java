package com.linyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.dto.SetmealDto;
import com.linyun.mapper.SetMealDishMapper;
import com.linyun.mapper.SetMealMapper;
import com.linyun.pojo.SetMeal;
import com.linyun.pojo.SetMealDish;
import com.linyun.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linyun
 * @date 2023/04/02/13:55
 */
@Service
@Transactional
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper,SetMeal> implements SetMealService {
    @Resource
    private SetMealMapper setMealMapper;
    @Resource
    private SetMealDishMapper setMealDishMapper;


    @Override
    public Rest<PageBean<SetmealDto>> conditionPageSelectByName(Integer pageTotal, Integer pageSize, String name) {
        pageTotal = (pageTotal - 1) * pageSize;
        if (name != null && name != "") {
            name = "%" + name + "%";
        }
        List<SetmealDto> setMeals = setMealMapper.conditionPageSelectByName(pageTotal, pageSize, name);
        Integer totalCount = setMealMapper.conditionGetCountByName(name);
        if (pageTotal > totalCount) {
            setMeals = setMealMapper.conditionPageSelectByName(totalCount - pageSize, pageSize, name);
        }
        return Rest.success(new PageBean<>(setMeals, totalCount));
    }


    @Override
    public Rest<SetmealDto> updatePageSelectById(Long id) {
        SetmealDto setmealDto = new SetmealDto();
        //查询setMeal对象数据
        SetMeal setMeal = setMealMapper.updateIndexSelectById(id);
        //查询setMealDishes的数据--->套餐信息
        List<SetMealDish> setMealDishes = setMealDishMapper.updateIndexSelectById(id);
        //把setMeal复制到SetmealDto对象里面
        BeanUtils.copyProperties(setMeal, setmealDto);
        //设置SetmealDto对象的SetmealDishes属性
        setmealDto.setSetmealDishes(setMealDishes);
        //返回结果
        return Rest.success(setmealDto);
    }


    @Override
    public Rest<String> insertMealAll(SetmealDto setmealDto) {
        setMealMapper.insertMealAll(setmealDto);
        List<SetMealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setMealDishMapper.insertMealDish(setmealDishes);
        return Rest.success("添加成功");
    }

    @Override
    public Rest<String> updateMealById(SetmealDto setmealDto) {
        setMealMapper.updateMealById(setmealDto);
        //删除setmeal_dish原有关于Meal的数据
        setMealDishMapper.deleteBySetmealId(setmealDto.getId());
        //重新添加setmeal菜单的数据
        List<SetMealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setMealDishMapper.insertMealDish(setmealDishes);
        return Rest.success("修改成功");
    }


    @Override
    public Rest<String> batchUpdateStatus(Integer status, List<Long> ids) {
        setMealMapper.batchUpdateStatus(status, ids);
        return Rest.success("修改成功");
    }

    @Override
    public Rest<String> batchDeleteById(List<Long> ids) {
        setMealMapper.batchDeleteById(ids);
        setMealDishMapper.batchDeleteBySetmealId(ids);
        return Rest.success("删除成功");
    }

    @Override
    public Rest<List<SetMeal>> list(SetMeal setMeal) {
        List<SetMeal> setMeals = setMealMapper.selectByCategoryIdAndStatus(setMeal);
        return Rest.success(setMeals);
    }
}
