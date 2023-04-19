package com.linyun.service;

import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.pojo.Employee;

public interface EmployeeService {
    /**
     * 登录
     * @param employee
     * @return
     */
    Rest<Employee> login(Employee employee);

    /**
     * 退出
     * @return
     */
    Rest<String> logOut();

    /**
     * 分页查询
     * @param pageTotal
     * @param pageSize
     * @param name
     * @return
     */
    Rest<PageBean<Employee>> indexPage(Integer pageTotal, Integer pageSize, String name);
    /**
     * 新增
     * @param employee
     * @return
     */
    Rest<String> insertEmployee(Employee employee);

    /**
     * 修改用户信息
     * @param employee
     * @return
     */
    Rest<String>  updateEmployee(Employee employee);

    /**
     * 按id查询修改页面信息
     * @param id
     * @return
     */
    Rest<Employee>  updatePageById(Long id);


}
