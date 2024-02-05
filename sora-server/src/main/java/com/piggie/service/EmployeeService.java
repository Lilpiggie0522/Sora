package com.piggie.service;

import com.piggie.dto.EmployeeLoginDTO;
import com.piggie.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
