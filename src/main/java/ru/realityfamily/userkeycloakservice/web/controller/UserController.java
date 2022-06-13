package ru.realityfamily.userkeycloakservice.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.realityfamily.userkeycloakservice.service.keycloack.KeyCloakService;
import ru.realityfamily.userkeycloakservice.web.dto.UserLoginRequest;
import ru.realityfamily.userkeycloakservice.web.dto.UserRequest;
import ru.realityfamily.userkeycloakservice.web.dto.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
//@RequestMapping("/user")
public class UserController {


    @Autowired
    KeyCloakService keycloakService;

    @Autowired
    @Qualifier("defaultMapper")
    ModelMapper mapper;


    // SUPPORT FOR SIGN IN
    @PostMapping(path = "/user/signin")
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

    @GetMapping(path = "/user/validateToken")
    public ResponseEntity<?> validateToken(HttpServletRequest request){
        try{
        return ResponseEntity.ok(keycloakService.validateToken(request.getHeader("Authorization")));}
        catch (Exception e){
            log.error("Got error on validate token: ", e);
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    /// USER
    @PostMapping("/user")
    public UserResponse addUser(@RequestBody UserRequest userreq) {
        return mapper.map(keycloakService.addUser(userreq),UserResponse.class);
    }

    @GetMapping(path = "/user/byUsername/{userName}")
    public UserResponse getUserByUsername(@PathVariable("userName") String userName) {
        UserRepresentation representation = keycloakService.getUserBuyName(userName);
        return mapper.map(representation, UserResponse.class);
    }

    @GetMapping(path = "/user/byKeyCloakId/{id}")
    public UserResponse getUserByKeycloakId(@PathVariable("id") String id) {
        UserRepresentation representation = keycloakService.getUserById(id);
        return mapper.map(representation, UserResponse.class);
    }

    @PutMapping(path = "/user/update/{userId}")
    public UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userDTO) {
        return mapper.map(keycloakService.updateUser(userId, userDTO), UserResponse.class);
    }

    @DeleteMapping(path = "/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        keycloakService.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/user/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId) {
        keycloakService.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping(path = "/user/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId) {
        keycloakService.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail for id: " + userId;
    }


    /// USERS
    @GetMapping(path = "/users/byGroupName/{group_name}")
    public List<UserResponse> getUsersByGroupName(@PathVariable("group_name") String groupName) {
        List<UserRepresentation> representations = keycloakService.getUsersByGroupName(groupName);
        return representations.stream().map( representation -> mapper.map(representation, UserResponse.class)).toList();
    }

    @GetMapping(path = "/users/byGroupId/{group_id}")
    public List<UserResponse> getUsersByGroupId(@PathVariable("group_id") String groupId) {
        List<UserRepresentation> representations = keycloakService.getUsersByGroupId(groupId);
        return representations.stream().map( representation -> mapper.map(representation, UserResponse.class)).toList();
    }


    /// ROLES
    @GetMapping(path = "/realmRoles/byUserName/{username}")
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
