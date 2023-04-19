package com.linyun.Employee;

import com.linyun.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author linyun
 * @date 2023/03/27/21:41
 */
@SpringBootTest
public class mapperTest {
    @Resource
    private EmployeeMapper mapper;

}