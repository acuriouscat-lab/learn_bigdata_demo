package com.example.springbootdemo.service;

import com.example.springbootdemo.Bean.Employee;
import com.example.springbootdemo.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeDao employeeDao;


    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public void insertEmployee(Employee employee) {
        employeeDao.insertEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        employeeDao.deleteEmployee(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }
}
