package com.linyun.controller;

import com.linyun.common.Rest;
import com.linyun.pojo.User;
import com.linyun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author linyun
 * @date 2023/04/04/10:29
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;



    /**
     * 发送手机验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Rest<String> sendMsg(@RequestBody User user) {
        return userService.sendMsg(user);
    }

    @PostMapping("/login")
    public Rest<User> login(@RequestBody Map map) {
        return userService.login(map);
    }
}
