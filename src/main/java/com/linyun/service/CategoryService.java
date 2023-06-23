package com.linyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.pojo.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    /**
     * 分类页面的分页查询
     *
     * @param pageTotal 页面启始下标
     * @param pageSize  页面条数
     * @return
     */
    Rest<PageBean<Category>> pageSelect(Integer pageTotal, Integer pageSize);

    /**
     * 新增菜品或套餐
     *
     * @param category
     * @return
     */
    Rest<String> insertCategory(Category category);

    /**
     * 修改菜品或套餐
     *
     * @param category
     * @return
     */
    Rest<String> updateCategoryById(Category category);

    /**
     * 新增菜品或套餐
     *
     * @param id
     * @return
     */
    Rest<String> deleteCategoryById(Long id);

    /**
     * 查询分类返回给meal和dish
     *
     * @param type
     * @return
     */
    Rest<List<Category>> dishSelectType(Category category);
}
