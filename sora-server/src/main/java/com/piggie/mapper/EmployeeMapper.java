package com.piggie.mapper;

import com.github.pagehelper.Page;
import com.piggie.dto.EmployeeDTO;
import com.piggie.dto.EmployeePageQueryDTO;
import com.piggie.entity.Employee;
import com.piggie.result.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user)" +
            "values" +
            "(#{name}," +
            "#{username}," +
            "#{password}," +
            "#{phone}," +
            "#{sex}," +
            "#{idNumber}," +
            "#{status}," +
            "#{createTime}," +
            "#{updateTime}," +
            "#{createUser}," +
            "#{updateUser})")
    void insert(Employee employee);

    /*List<Employee> pageQuery(Integer start, Integer size);*/


    /*@Select("select count(*) from employee")
    Long count();*/

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    void update(Employee employee);

    Employee getEmployeeById(Integer id);
}
