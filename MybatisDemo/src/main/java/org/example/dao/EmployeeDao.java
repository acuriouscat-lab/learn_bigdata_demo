package org.example.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.Bean.Employee;

import java.util.List;

public interface EmployeeDao
{
    @Select("select * from employee where id = #{id}")
    Employee getEmployeeById(Integer id);

    @Select("select * from employee")
    List<Employee> getAll();

    @Insert("insert into employee(last_name,gender,email) values(#{lastName},#{gender},#{email})")
    void insertEmployee(Employee employee);

    @Update("update employee set last_name=#{lastName} , gender = #{gender } , email=#{email} " +
            "        where id = #{id}")
    void updateEmployee(Employee employee);

    @Delete("delete  from employee where id = #{id}")
    void deleteEmployeeById(Integer id);
}
