package com.linyun.controller;

import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.dto.SetmealDto;
import com.linyun.pojo.SetMeal;
import com.linyun.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linyun
 * @date 2023/04/02/13:57
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Resource
    private SetMealService setMealService;

    /**
     * 分页查询
     *
     * @param page     页面启始下标索引
     * @param pageSize 页面数
     * @param name     查询附带信息 可以为null
     * @return Rest<PageBean < SetmealDto>>
     */
    @GetMapping("/page")
    public Rest<PageBean<SetmealDto>> conditionPageSelectByName(Integer page, Integer pageSize, String name) {
        log.info("当前页数{}，页面大小{}，条件{}", page, pageSize, name);
        return setMealService.conditionPageSelectByName(page, pageSize, name);
    }
    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @Cacheable(value = "setMealCache",key = "'setMeal_'+#setmeal.categoryId")
    @GetMapping("/list")
    public Rest<List<SetMeal>> list(SetMeal setmeal){

        return setMealService.list(setmeal);
    }
    /**
     * 为修改页面查询信息
     *
     * @param id 需要查询的id
     * @return Rest<SetmealDto>
     */
    @GetMapping("/{id}")
    public Rest<SetmealDto> updateIndexSelectById(@PathVariable Long id) {
        log.info("修改查询的id为：{}", id);
        return setMealService.updatePageSelectById(id);
    }

    /**
     * 新增套餐
     *
     * @param setmealDto 新增的参数
     * @return Rest<String>
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @PostMapping
    public Rest<String> insertMealAll(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息有：{}", setmealDto);
        return setMealService.insertMealAll(setmealDto);
    }

    /**
     * 修改套餐信息
     * @param setmealDto 修改信息
     * @return
     */
    @PutMapping
    public Rest<String> updateMeal(@RequestBody SetmealDto setmealDto) {
        log.info("修改的信息有：{}", setmealDto);
        return setMealService.updateMealById(setmealDto);
    }

    /**
     * 批量起售或停售套餐
     * @param status 套餐状态
     * @param ids 需要调整的ids
     * @return
     */
    @PostMapping("status/{status}")
    public Rest<String> batchUpdateStatus(@PathVariable Integer status,@RequestParam List<Long> ids){
        return setMealService.batchUpdateStatus(status,ids);
    }

    /**
     * 逻辑删除或者批量逻辑删除 套餐
     * @param ids 需要删除的ids
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @DeleteMapping
    public Rest<String> batchDeleteByIds(@RequestParam List<Long> ids) {
        log.info("要删除的id有：{}", ids);
        return setMealService.batchDeleteById(ids);
    }
}
