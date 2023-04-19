package com.linyun.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author linyun
 * @date 2023/03/28/19:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {

    //当前页数据
    private List<T> records;
    //总记录数
    private Integer total;

}
