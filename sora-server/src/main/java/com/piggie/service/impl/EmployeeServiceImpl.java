package com.piggie.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piggie.constant.JwtClaimsConstant;
import com.piggie.constant.MessageConstant;
import com.piggie.constant.PasswordConstant;
import com.piggie.constant.StatusConstant;
import com.piggie.context.BaseContext;
import com.piggie.dto.EmployeeDTO;
import com.piggie.dto.EmployeeLoginDTO;
import com.piggie.dto.EmployeePageQueryDTO;
import com.piggie.entity.Employee;
import com.piggie.exception.AccountLockedException;
import com.piggie.exception.AccountNotFoundException;
import com.piggie.exception.PasswordErrorException;
import com.piggie.mapper.EmployeeMapper;
import com.piggie.properties.JwtProperties;
import com.piggie.result.PageResult;
import com.piggie.service.EmployeeService;
import com.piggie.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO Add MD5 encryption, then compare to databases
         password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    /**
     * Create new employee
     */
    public void save(EmployeeDTO employeeDTO) {
        //  copy properties from dto to Employee pojo
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //  set status to default 1
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //  Todo change createUser to current user dynamically
        employee.setCreateUser((Long) BaseContext.getCurrentId());
        employee.setUpdateUser((Long)BaseContext.getCurrentId());
        employeeMapper.insert(employee);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        /*int size = employeePageQueryDTO.getPageSize();
        int start = (employeePageQueryDTO.getPage() - 1) * size;
        List<Employee> employees = employeeMapper.pageQuery(start, size);
        Long count = employeeMapper.count();
        PageResult pageResult = new PageResult();
        pageResult.setTotal(count);
        pageResult.setRecords(employees);
        return pageResult;*/

        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        List<Employee> result = page.getResult();
        long total = page.getTotal();
        return new PageResult(total, result);
    }

    @Override
    public void accountSuspend(Integer status, Long id) {
        Employee employee = Employee.builder().id(id).status(status).build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        employee.setPassword("****");
        return employee;
    }

    @Override
    public void updateById(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}
