package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.EmployeeMapper;
import org.example.pojo.Employee;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/5 18:54
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

}
