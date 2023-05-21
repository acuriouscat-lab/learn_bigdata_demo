package org.example.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Bean.Employee;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EmployeeDaoTest {


    private SqlSessionFactory sqlSessionFactory;

    {
        try {
            InputStream is = Resources.getResourceAsStream("mabatis.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void getEmployeeById() {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            System.out.println(session);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(employee);
            System.out.println("---");
        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void getAll() {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            List<Employee> employeeList = mapper.getAll();

            System.out.println(employeeList);
        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void insertEmployee() {
        SqlSession session = sqlSessionFactory.openSession(true);

        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            Employee employee = new Employee(null, "lisi", "male", "1332");
            mapper.insertEmployee(employee);

        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void updateEmployee() {
        SqlSession session = sqlSessionFactory.openSession(true);

        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            Employee employee1 = mapper.getEmployeeById(2);
            employee1.setEmail("123");
            mapper.updateEmployee(employee1);

        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void deleteEmployee() {
        SqlSession session = sqlSessionFactory.openSession(true);

        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            mapper.deleteEmployeeById(4);

        } catch (Exception e) {
            session.close();
        }
    }
}