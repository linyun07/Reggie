package com.linyun;

import com.linyun.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ReggieApplicationTests {

    @Resource
    private EmployeeMapper mapper;
    @Test
    void selectTest(){
        System.out.println(mapper.selectAllByPage(1, 2, null));
        System.out.println(mapper.selectById(1L));
        System.out.println(mapper.searchByUsernameAndPassword("lin", "123456"));
        System.out.println(mapper.SelectByUsername("lin"));
    }
}
