package com.linyun.service.impl;

import com.linyun.common.PageBean;
import com.linyun.common.Rest;
import com.linyun.mapper.EmployeeMapper;
import com.linyun.pojo.Employee;
import com.linyun.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author linyun
 * @date 2023/03/29/16:31
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper mapper;
    @Resource
    private HttpServletRequest request;


    /**
     * 登录
     *
     * @param employee
     * @return
     */
    @Override
    public Rest<Employee> login(Employee employee) {

        Employee emp = mapper.SelectByUsername(employee.getUsername());
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的用户名username查询数据库
        Rest<Employee> error = JudgeEmp(emp, password);
        if (error != null) return error;
        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return Rest.success(emp);
    }

    /**
     * 退出
     *
     * @return
     */
    @Override
    public Rest<String> logOut() {
        request.getSession().removeAttribute("employee");
        return Rest.success("登出成功");
    }

    /**
     * 分页查询
     *
     * @param pageTotal 用户启始索引下标
     * @param pageSize  用户页面条数
     * @param name      查询用户名信息
     * @return
     */
    @Override
    public Rest<PageBean<Employee>> indexPage(Integer pageTotal, Integer pageSize, String name) {
        if (name != null && name != "") {
            name = "%" + name + "%";
        }
        pageTotal = (pageTotal - 1) * pageSize;
        List<Employee> employees = mapper.selectAllByPage(pageTotal, pageSize, name);
        Integer countTotal = mapper.conditionGetCount(name);
        if (pageTotal > countTotal) {
            employees = mapper.selectAllByPage(countTotal - pageSize, pageSize, name);
        }
        return Rest.success(new PageBean<>(employees, countTotal));
    }

    /**
     * 判断用户名信息 返回响应信息
     *
     * @param emp
     * @param password
     * @return
     */
    private Rest<Employee> JudgeEmp(Employee emp, String password) {
        if (emp == null) {
            //3、如果没有查询到则返回登录失败结果
            return Rest.error("登录失败，请查验用户或密码！");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            //4、密码比对，如果不一致则返回登录失败结果
            return Rest.error("登录失败，请查验用户或密码！");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return Rest.error("登录失败，用户已被禁用！");
        }
        return null;
    }

    /**
     * 新增用户
     * @param employee
     * @return
     */
    @Override
    public Rest<String> insertEmployee(Employee employee) {

        log.info("新增数据{}", employee);
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        mapper.insertEmployee(employee);
        return Rest.success("添加成功");
    }

    /**
     * 修改用户信息
     * @param employee
     * @return
     */
    @Override
    public Rest<String> updateEmployee(Employee employee) {
        mapper.updateEmployee(employee);
        return Rest.success("修改成功");
    }

    /**
     * 按id查询修改用户页面的信息
     * @param id
     * @return
     */
    @Override
    public Rest<Employee> updatePageById(Long id) {
        Employee employee = mapper.selectById(id);
        return Rest.success(employee);
    }


}
