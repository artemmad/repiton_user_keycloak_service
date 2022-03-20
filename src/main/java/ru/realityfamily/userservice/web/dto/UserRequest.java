package ru.realityfamily.userservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private String firstname;
    private String lastName;
    private List<String> groupsNames;
}
