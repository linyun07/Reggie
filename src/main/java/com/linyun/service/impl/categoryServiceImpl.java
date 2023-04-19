package com.linyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyun.common.CustomException;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.mapper.CategoryMapper;
import com.linyun.mapper.DishMapper;
import com.linyun.mapper.SetMealMapper;
import com.linyun.pojo.Category;
import com.linyun.service.categoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linyun
 * @date 2023/03/30/9:13
 */
@Service
public class categoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements categoryService {
    @Resource
    private CategoryMapper mapper;
    @Resource
    private DishMapper dishMapper;
    @Resource
    private SetMealMapper setMealMapper;
    /**
     * 分类页面的分页查询
     * @param pageTotal 页面启始下标
     * @param pageSize 页面条数
     * @return
     */
    @Override
    public Rest<PageBean<Category>> pageSelect(Integer pageTotal, Integer pageSize) {
        pageTotal=(pageTotal-1)*pageSize;
        List<Category> categories = mapper.pageSelect(pageTotal, pageSize);
        Integer totalCount = mapper.conditionGetCount();
        if (pageTotal>totalCount){
            categories=mapper.pageSelect(totalCount-pageSize,pageSize);
        }
        return Rest.success(new PageBean<Category>(categories,totalCount));
    }
    /**
     * 新增菜品或套餐
     *
     * @param category
     * @return
     */
    @Override
    public Rest<String> insertCategory(Category category) {
        mapper.insertCategory(category);
        return Rest.success("添加成功");
    }

    /**
     * 修改菜品或套餐
     *
     * @param category
     * @return
     */
    @Override
    public Rest<String> updateCategoryById(Category category) {
        mapper.updateById(category);
        return Rest.success("修改成功");
    }

    /**
     * 删除菜品或套餐
     *
     * @param ids
     * @return
     */
    @Override
    public Rest<String> deleteCategoryById(Long ids) {
        System.out.println(ids);
        Integer dishCount = dishMapper.selectCountByCategoryId(ids);
        if (dishCount > 0) {
            //绑定了菜品，不能删除，需要抛出异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        Integer setMealCount = setMealMapper.selectCountByCategoryId(ids);
        if (setMealCount > 0) {
            //绑定了套餐，不能删除，需要抛出异常
            throw new CustomException("当前分类关联了套餐，不能删除");
        }
        //没有关联任何东西，可以正常删除
        mapper.deleteCategoryById(ids);
        return Rest.success("删除成功");
    }

    /**
     * 查询分类返回给meal和dish
     * @param category
     * @return
     */
    @Override
    public Rest<List<Category>> dishSelectType(Category category) {
     return null;
    }
}
