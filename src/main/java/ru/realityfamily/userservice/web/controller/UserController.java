package ru.realityfamily.userservice.web.controller;

import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.realityfamily.userservice.service.keycloack.KeyCloakService;
import ru.realityfamily.userservice.web.dto.UserLoginRequest;
import ru.realityfamily.userservice.web.dto.UserRequest;
import ru.realityfamily.userservice.web.dto.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    KeyCloakService keycloakService;

    @Autowired
    @Qualifier("defaultMapper")
    ModelMapper mapper;


    // SUPPORT FOR SIGN IN
    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody UserLoginRequest userLoginRequest) {
        try{
            return ResponseEntity.ok(keycloakService.getAuthToken(userLoginRequest));}
        catch(UnsupportedOperationException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    /// USER
    @PostMapping
    public UserResponse addUser(@RequestBody UserRequest userreq) {
        return mapper.map(keycloakService.addUser(userreq),UserResponse.class);
    }

    @GetMapping(path = "/{userName}")
    public UserResponse getUser(@PathVariable("userName") String userName) {
        UserRepresentation representation = keycloakService.getUserBuyName(userName);
        return mapper.map(representation, UserResponse.class);
    }

    @PutMapping(path = "/update/{userId}")
    public UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userDTO) {
        return mapper.map(keycloakService.updateUser(userId, userDTO), UserResponse.class);

    }

    @DeleteMapping(path = "/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        keycloakService.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId) {
        keycloakService.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping(path = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId) {
        keycloakService.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }


    /// ROLES
    @GetMapping(path = "realmRoles/byUserName/{username}")
    public List<RoleRepresentation> getRolesOfClient(@PathVariable("username") String username) {
        return keycloakService.getUserRolesRealmByName(username);
    }

    @GetMapping(path = "/realmRoles/byUserId/{user_id}")
    public List<RoleRepresentation> getRolesOfUserByUserId(@PathVariable("user_id") String userId) {
        return keycloakService.getUserRolesRealmByUserId(userId);
    }

    @GetMapping(path = "/roles")
    public List<org.keycloak.representations.idm.RoleRepresentation> getAllRoles() {
        return keycloakService.getRolesAll();
    }


    /// GROUPS
    @GetMapping(path = "/groups")
    public List<GroupRepresentation> getAllGroups() {
        return keycloakService.getAllGroups();
    }

    @GetMapping(path = "/groups/byUserName/{username}")
    public List<GroupRepresentation> getGroupsOfUserByName(@PathVariable("username") String username){
        return keycloakService.getUserGroupsByName(username);
    }

    @GetMapping(path = "/groups/byUserId/{user_id}")
    public List<GroupRepresentation> getGroupsOfUserById(@PathVariable("user_id") String userId){
        return keycloakService.getUserGroupsById(userId);
    }

}
