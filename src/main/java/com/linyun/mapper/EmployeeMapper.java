package com.linyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyun.pojo.Employee;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author linyun
 * @date 2023/03/27/21:08
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 判断用户名和密码登录
     * @param username
     * @param password
     * @return
     */
    @Select("select *from employee where username=#{username} and password=#{password}")
    Employee searchByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 先通过账户名username查询是否有这个对象
     * @param username
     * @return
     */
    @Select("select *from employee where username like #{username}  ")
    Employee SelectByUsername(String username);



    /**
     * 分页查询
     * @param pageTotal 页面启始下标
     * @param pageSize 页面条数
     * @param name 查询信息
     * @return
     */
    List<Employee> selectAllByPage(@Param("pageTotal") Integer pageTotal, @Param("pageSize") Integer pageSize, @Param("name") String name);

    /**
     * 查询你页面总条数
     * @param name
     * @return
     */
    Integer conditionGetCount(String name);

    /**
     * 新增用户
     * @param employee
     * @return
     */
    Integer insertEmployee(Employee employee);

    /**
     * 修改用户信息
     * @param employee
     * @return
     */
    Integer updateEmployee(Employee employee);

    /**
     * 查询修改页面信息
     * @param id
     * @return
     */
    @Select("select *from employee where id=#{id}")
    Employee updatePageById(Long id);

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Delete("delete from employee where id=#{id};")
    Integer deleteById(Long id);



}
