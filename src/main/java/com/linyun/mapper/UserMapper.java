package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author linyun
 * @date 2023/04/04/10:26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据电话查询用户是否已注册
     * @param phone
     * @return
     */
    @Select("select *from user where phone=#{phone}")
    User selectUserByPhone(String phone);

    /**
     * 新增用户
     *
     * @param user
     */
    @Insert("insert into user(id,phone) values (#{id},#{phone})")
    void insertUser(User user);

    /**
     * 根据id查用户
     * @param id
     * @return
     */
    @Select("select *from user where id=#{id}")
    User selectUserById(Long id);
}
