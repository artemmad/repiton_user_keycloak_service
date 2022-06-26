package ru.realityfamily.userkeycloakservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequestWithoutPassword {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> groupsNames;
}
