package com.piggie.service;

import com.piggie.dto.EmployeeDTO;
import com.piggie.dto.EmployeeLoginDTO;
import com.piggie.dto.EmployeePageQueryDTO;
import com.piggie.entity.Employee;
import com.piggie.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void accountSuspend(Integer status, Long id);

    Employee getEmployeeById(Integer id);

    void updateById(EmployeeDTO employeeDTO);
}
