package com.linyun.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.pojo.Category;
import com.linyun.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linyun
 * @date 2023/03/29/19:37
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Rest<PageBean<Category>> pageSelect(Integer page, Integer pageSize) {
        return categoryService.pageSelect(page, pageSize);
    }

    /**
     * 返回给dish和meal分类类别
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Rest<List<Category>> dishSelectType( Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return Rest.success(list);
    }

    /**
     * 新增菜品或者套餐
     * @param category
     * @return
     */
    @PostMapping
    public Rest<String> insertCategory(@RequestBody Category category) {
        return categoryService.insertCategory(category);
    }

    /**
     * 修改菜品或套餐信息
     * @param category
     * @return
     */
    @PutMapping
    public Rest<String> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategoryById(category);
    }

    /**
     * 删除菜品或套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public Rest<String> deleteCategoryById(Long ids) {
        return categoryService.deleteCategoryById(ids);
    }

}
