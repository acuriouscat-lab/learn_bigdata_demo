package org.example.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {


    private Integer id;
    private String lastName;
    private String gender;
    private String email;

}
