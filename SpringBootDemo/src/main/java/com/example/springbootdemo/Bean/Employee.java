package com.example.springbootdemo.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {


    private Integer id;
    private String lastName;
    private String gender;
    private String email;

}
