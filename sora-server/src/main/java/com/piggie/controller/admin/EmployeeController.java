package com.piggie.controller.admin;

import com.piggie.constant.JwtClaimsConstant;

import com.piggie.dto.EmployeeDTO;
import com.piggie.dto.EmployeeLoginDTO;
import com.piggie.dto.EmployeePageQueryDTO;
import com.piggie.entity.Employee;
import com.piggie.properties.JwtProperties;
import com.piggie.result.PageResult;
import com.piggie.result.Result;
import com.piggie.service.EmployeeService;
import com.piggie.utils.JwtUtil;
import com.piggie.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "employee interface")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * Create new employee
     */
    @PostMapping
    @ApiOperation("create new employee")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("create new employee {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("employee page query")
    public Result page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("page search");
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("suspend or unsuspend employee account")
    public Result accountSuspend(@PathVariable(value = "status") Integer status, @RequestParam(value = "id") Long id) {
        log.info("status is {}, id is {}", status, id);
        employeeService.accountSuspend(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("get employee by Id")
    public Result<Employee> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("update employee by Id")
    public Result updateById(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateById(employeeDTO);
        return Result.success();
    }
}
