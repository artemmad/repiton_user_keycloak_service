package ru.realityfamily.userkeycloakservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private long createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean totp;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> realmRoles;
    private List<String> groups;

}
