package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * @author Maplerain
 * @date 2023/4/5 21:22
 **/
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    private static final boolean HAS_META_OBJECT_HANDLER = false;

    /**
     * 员工登录请求处理
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null) {
            return Result.error("用户不存在！");
        }

        if (!emp.getPassword().equals(password)) {
            return Result.error("密码错误！");
        }

        if (emp.getStatus() == 0) {
            return Result.error("该账号已被禁用！");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 员工退出请求处理
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param request
     * @param employee
     * @param defaultPassword
     * @return
     */
    @PostMapping
    public Result save(HttpServletRequest request, @RequestBody Employee employee,
                       @Value("${default-value.employee-password}") String defaultPassword) {

        employee.setPassword(DigestUtils.md5DigestAsHex(defaultPassword.getBytes()));

        if (!HAS_META_OBJECT_HANDLER) {
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());

            Long id = (Long) request.getSession().getAttribute("employee");

            employee.setCreateUser(id);
            employee.setUpdateUser(id);
        }

        boolean result = employeeService.save(employee);

        return result ? Result.success() : Result.error("SAVEERROR");
    }

    /**
     * 分页查询员工列表
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result page(int page, int pageSize, String name) {
        // 分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 查询语句构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isNullOrEmpty(name), Employee::getName, name);
        // 降序排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 修改员工信息
     *
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public Result update(HttpServletRequest request, @RequestBody Employee employee) {

        if (!HAS_META_OBJECT_HANDLER) {
            Long empId = (Long) request.getSession().getAttribute("employee");
            employee.setUpdateTime(LocalDateTime.now());
            employee.setUpdateUser(empId);
        }

        employeeService.updateById(employee);

        return Result.success("员工信息修改成功");
    }

    /**
     * 根据 id 查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        }
        return Result.error("没有查询到对应的员工 id");
    }

}