package com.linyun;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author linyun
 * @date 2023/04/13/20:28
 */
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void StringTest() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name11","lisi");
        valueOperations.getAndExpire("name11", Duration.ofDays(30));
        Object name = valueOperations.get("name");
        System.out.println(name);
    }
}
