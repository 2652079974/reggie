package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author Maplerain
 * @date 2023/4/5 18:52
 **/
@Mapper
@Component
public interface EmployeeMapper extends BaseMapper<Employee> {

}
