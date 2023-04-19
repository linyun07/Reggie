package com.linyun.service;

import com.linyun.common.Rest;
import com.linyun.pojo.User;

import java.util.Map;

/**
 * @author linyun
 * @date 2023/04/04/10:27
 */
public interface UserService {
    /**
     * 发送验证码
     * @param user
     * @return
     */
    Rest<String> sendMsg(User user);

    /**
     * 实现登录，如果没有这个用户就注册
     * @param map 存储phone和验证码code
     * @return
     */
    Rest<User> login(Map map);
}
