package com.example.springbootdemo.dao;

import com.example.springbootdemo.Bean.Employee;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface EmployeeDao {

    @Select("select * from employee where id = #{id}")
    Employee getEmployeeById(Integer id);
    @Select("select * from employee")
    List<Employee> getAll();

//    @Insert("insert into employee(last_name,gender,email) values(#{lastName},#{gender},#{email}")
    @Insert("insert into employee(last_name,gender,email) values(#{lastName},#{gender},#{email})")
    void insertEmployee(Employee employee);

    //更新员工
    @Update("update employee set last_name = #{lastName} , gender = #{gender} ,email = #{email} where id = #{id}")
    void updateEmployee(Employee employee);

    @Delete("delete from employee where id = #{id}")
    void deleteEmployee(Integer id);


}
