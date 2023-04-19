package com.linyun.User;

import com.linyun.mapper.UserMapper;
import com.linyun.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author linyun
 * @date 2023/04/04/20:17
 */
@SpringBootTest
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void InsertTesl(){
        User user = new User();
        user.setPhone("13699997777");
        user.setId(23239L);
        userMapper.insertUser(user);
    }
}
