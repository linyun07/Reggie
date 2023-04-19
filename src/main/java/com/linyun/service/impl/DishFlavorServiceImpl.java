package com.linyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyun.mapper.DishFlavorMapper;
import com.linyun.pojo.DishFlavor;
import com.linyun.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
