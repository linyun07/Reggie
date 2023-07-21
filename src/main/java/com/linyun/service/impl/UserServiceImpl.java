package com.linyun.service.impl;

import com.linyun.common.Rest;
import com.linyun.mapper.UserMapper;
import com.linyun.pojo.User;
import com.linyun.service.UserService;
import com.linyun.untils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author linyun
 * @date 2023/04/04/10:28
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private HttpSession session;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Rest<String> sendMsg(User user) {
        //获取用户输入电话
        String phone = user.getPhone();

        //判断这个电话是否为空
        if (StringUtils.isNotEmpty(phone)) {

            //获取验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();

            //通过阿里云短信服务发送短信
//            SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", phone, code);

            //控制台输出短信 代替阿里云的短信服务
            log.info("信息是：{}", code);

            //把验证码存进Redis
            redisTemplate.opsForValue().set("code",code,5, TimeUnit.MINUTES);

            //发送成功标识
            return Rest.success("手机验证码短信发送成功");
        }
        //直接返回就是电话出现问题
        return Rest.error("请检查你的手机号和验证码是否正确");
    }

    @Override
    public Rest<User> login(Map map) {
        log.info("map里面的数据是：" + map.toString());
        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();
        //获取Redis里面保存的验证码
        String codeInSession = (String) redisTemplate.opsForValue().get("code");
        //对比页面传回来的code和session里面的code
        if (codeInSession != null && codeInSession.equals(code)) {
            //比对成功 通过phone查询是否存在这个user
            User user = userMapper.selectUserByPhone(phone);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userMapper.insertUser(user);
            }
            session.setAttribute("user", user.getId());
            //登录成功删除redis缓存的验证码
            redisTemplate.delete("code");
            return Rest.success(user);
        }
        return Rest.error("请检查你的手机号和验证码是否正确");
    }
}
