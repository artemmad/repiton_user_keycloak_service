package ru.realityfamily.userkeycloakservice.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AuthResponse {
    private String access_token;
    private Long expires_in;
    private Long refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private String session_state;
    private String error;
    private String error_description;
    private String error_uri;

    private String id;
    private String username;
    private List<String> roles;
}
