package com.linyun.controller;

import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.pojo.Employee;
import com.linyun.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 对员工表页面进行操作
 *
 * @author linyun
 * @date 2023/03/27/21:10
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;


    /**
     * 登录
     *
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Rest<Employee> login(@RequestBody Employee employee) {
        return employeeService.login(employee);
    }

    /**
     * 登出 删除session
     *
     * @return
     */
    @PostMapping("/logout")
    public Rest<String> logOut() {
        return employeeService.logOut();
    }

    /**
     * 分页操作
     *
     * @param page     页面启始索引下标
     * @param pageSize 页面条数
     * @param name     查询用户名
     * @return
     */
    @GetMapping("/page")
    //形参属性要和url里面的名字一样
    public Rest<PageBean<Employee>> indexPage(Integer page, Integer pageSize, String name) {
        return employeeService.indexPage(page, pageSize, name);
    }

    /**
     * 新增员工
     * @param employee 新增员工信息
     * @return
     */
    @PostMapping
    public Rest<String> saveEmployee(@RequestBody Employee employee) {
        return employeeService.insertEmployee(employee);
    }

    /**
     * 修改页面的信息
     *
     * @param id 根据id来查询
     * @return
     */
    @GetMapping("/{id}")
    public Rest<Employee> updatePageById(@PathVariable Long id) {
        return employeeService.updatePageById(id);
    }

    /**
     * 修改员工信息
     *
     * @param employee 员工信息
     * @return
     */
    @PutMapping
    public Rest<String> updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }


}
