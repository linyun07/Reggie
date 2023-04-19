package com.linyun.dto;


import com.linyun.pojo.SetMeal;
import com.linyun.pojo.SetMealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends SetMeal {

    private List<SetMealDish> setmealDishes;

    private String categoryName;
}
