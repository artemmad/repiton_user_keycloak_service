package ru.realityfamily.userkeycloakservice.service.keycloack;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.realityfamily.userkeycloakservice.service.keycloack.entity.Credentials;
import ru.realityfamily.userkeycloakservice.service.keycloack.entity.TokenValidateResponse;
import ru.realityfamily.userkeycloakservice.web.dto.AuthResponse;
import ru.realityfamily.userkeycloakservice.web.dto.UserLoginRequest;
import ru.realityfamily.userkeycloakservice.web.dto.UserRequest;

import javax.ws.rs.core.Response;
import java.util.*;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class KeyCloakService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl; //ссылку от корня еще до контроллера auth всегда, я не ебу почему, но они это считают корнем своего проекта ¯\_(ツ)_/¯

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public UserRepresentation addUser(UserRequest userDTO) {

        UserRepresentation user = new UserRepresentation();
        if (userDTO.getUserName() != null && !userDTO.getUserName().isBlank()) {
            user.setUsername(userDTO.getUserName());
        } else {
            user.setUsername(userDTO.getEmail());
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            CredentialRepresentation credential = Credentials
                    .createPasswordCredentials(userDTO.getPassword());
            user.setCredentials(Collections.singletonList(credential));
        }

        user.setEnabled(true);
        user.setGroups(userDTO.getGroupsNames());

        UsersResource instance = getInstanceUsersResource();
        Response res = instance.create(user);

        // get the created User Id from keycloakResponse
        String[] tmp = res.getLocation().toString().split("/");
        String userId = tmp[tmp.length - 1];
        System.out.println(userId);

        return getUserById(userId);

    }


    public UserRepresentation getUserById(String id) {
        UserResource userResource = getInstanceUsersResource().get(id);
        UserRepresentation representation = userResource.toRepresentation();
        return addRoleAndGroupToUserRepresentation(representation);
    }

    public UserRepresentation getUserBuyName(String userName) {
        UsersResource usersResource = getInstanceUsersResource();
        UserRepresentation representation = usersResource.search(userName, true).get(0);

        //add not included fields like realmRoles and groups
        return addRoleAndGroupToUserRepresentation(representation);
    }

    public UserRepresentation getUserBuyEmail(String userEmail) {
        UsersResource usersResource = getInstanceUsersResource();
        UserRepresentation representation = usersResource.search("", "", "", userEmail, 0, 1).get(0);

        //add not included fields like realmRoles and groups
        return addRoleAndGroupToUserRepresentation(representation);
    }

    public List<UserRepresentation> getUsersByGroupName(String groupName) {
        GroupsResource groupsResource = getInstanceGroupsResource();
        List<GroupRepresentation> groupRepresentations = groupsResource.groups(groupName, 0, 1);
        GroupResource group = groupsResource.group(groupRepresentations.get(0).getId());
        return group.members();
    }

    public List<UserRepresentation> getUsersByGroupId(String groupId) {
        GroupsResource groupsResource = getInstanceGroupsResource();
        GroupResource group = groupsResource.group(groupId);
        return group.members();
    }

    /***
     * Fill all effective roles and groups data for provided UserRepresentation object
     * @param userRepresentation user representation for selected user
     * @return filled up UserRepresentation object
     */
    public UserRepresentation addRoleAndGroupToUserRepresentation(UserRepresentation userRepresentation) {
        //add roles to the representation
        UsersResource usersResource = getInstanceUsersResource();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userRepresentation.setRealmRoles(userResource.roles().realmLevel().listEffective().stream().map(RoleRepresentation::getName).toList()); // get effective realm roles of user and flat it to List<String> from List<RoleRepresentation>

        //add groups to the representation
        userRepresentation.setGroups(userResource.groups().stream().map(GroupRepresentation::getName).toList()); // get assigned groups of user and flat it to List<String> from List<RoleRepresentation>
        return userRepresentation;
    }

    public List<RoleRepresentation> getUserRolesRealmByName(String name) {
        UsersResource usersResource = getInstanceUsersResource();
        UserRepresentation userRepresentation = usersResource.search(name, true).get(0);
        UserResource userResource = usersResource.get(userRepresentation.getId());
        RoleScopeResource rolesResource = userResource.roles().realmLevel();
        return rolesResource.listEffective();
    }

    public List<RoleRepresentation> getUserRolesRealmByUserId(String userId) {
        UsersResource usersResource = getInstanceUsersResource();
        UserResource userResource = usersResource.get(userId);
        RoleScopeResource rolesResource = userResource.roles().realmLevel();
        return rolesResource.listEffective();
    }

    public List<org.keycloak.representations.idm.RoleRepresentation> getRolesAll() {
        RolesResource rolesResource = getInstanceRolesResource();
        return rolesResource.list();
    }


    public UserRepresentation updateUser(String userId, UserRequest userDTO) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setGroups(userDTO.getGroupsNames());

        UsersResource usersResource = getInstanceUsersResource();
        usersResource.get(userId).update(user);

        return getUserById(userId);
    }

    public void deleteUser(String userId) {
        UsersResource usersResource = getInstanceUsersResource();
        usersResource.get(userId).remove();
    }


    public void sendVerificationLink(String userId) {
        UsersResource usersResource = getInstanceUsersResource();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String userId) {
        UsersResource usersResource = getInstanceUsersResource();

        usersResource.get(userId)
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @SneakyThrows
    public AuthResponse getAuthToken(UserLoginRequest userLoginRequest) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(serverUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        if (userLoginRequest.getUsername() != null && !userLoginRequest.getUsername().isEmpty()) {
            AccessTokenResponse accessTokenResponse = authzClient.obtainAccessToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());

            UserRepresentation user = getUserBuyName(userLoginRequest.getUsername());
            return AuthResponse.builder()
                    .access_token(accessTokenResponse.getToken())
                    .expires_in(accessTokenResponse.getExpiresIn())
                    .refresh_expires_in(accessTokenResponse.getRefreshExpiresIn())
                    .refresh_token(accessTokenResponse.getRefreshToken())
                    .token_type(accessTokenResponse.getTokenType())
                    .session_state(accessTokenResponse.getSessionState())
                    .error(accessTokenResponse.getError())
                    .error_description(accessTokenResponse.getErrorDescription())
                    .error_uri(accessTokenResponse.getErrorUri())

                    .id(user.getId())
                    .username(user.getUsername())
                    .roles(user.getGroups())
                    .build();
        } else if (userLoginRequest.getEmail() != null && !userLoginRequest.getEmail().isEmpty()) {
            AccessTokenResponse accessTokenResponse = authzClient.obtainAccessToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());

            UserRepresentation user = getUserBuyEmail(userLoginRequest.getEmail());
            return AuthResponse.builder()
                    .access_token(accessTokenResponse.getToken())
                    .expires_in(accessTokenResponse.getExpiresIn())
                    .refresh_expires_in(accessTokenResponse.getRefreshExpiresIn())
                    .refresh_token(accessTokenResponse.getRefreshToken())
                    .token_type(accessTokenResponse.getTokenType())
                    .session_state(accessTokenResponse.getSessionState())
                    .error(accessTokenResponse.getError())
                    .error_description(accessTokenResponse.getErrorDescription())
                    .error_uri(accessTokenResponse.getErrorUri())

                    .id(user.getId())
                    .username(user.getUsername())
                    .roles(user.getGroups())
                    .build();
        } else {
            throw new UnsupportedOperationException("There is no username or email in request. If email and username provided, username have priority");
        }

    }


    /// GROUPS
    public List<GroupRepresentation> getAllGroups() {
        GroupsResource groupsResource = getInstanceGroupsResource();
        return groupsResource.groups();
    }

    public List<GroupRepresentation> getUserGroupsByName(String username) {
        UsersResource usersResource = getInstanceUsersResource();
        UserRepresentation userRepresentation = usersResource.search(username, true).get(0);
        UserResource userResource = usersResource.get(userRepresentation.getId());
        List<GroupRepresentation> groups = userResource.groups();
        return groups;
    }


    public List<GroupRepresentation> getUserGroupsById(String userId) {
        UsersResource usersResource = getInstanceUsersResource();
        UserResource userResource = usersResource.get(userId);
        List<GroupRepresentation> groups = userResource.groups();
        return groups;
    }


    public UsersResource getInstanceUsersResource() {
        return keycloak.realm(realm).users();
    }

    public RolesResource getInstanceRolesResource() {
        return keycloak.realm(realm).roles();
    }

    public GroupsResource getInstanceGroupsResource() {
        return keycloak.realm(realm).groups();
    }


    public AuthResponse validateToken(String authorization) {
        String url = serverUrl + "/realms/" + realm + "/protocol/openid-connect/userinfo";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<TokenValidateResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TokenValidateResponse.class);
        TokenValidateResponse tvr = response.getBody();
        UserRepresentation user = getUserById(tvr.sub);
        return AuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getGroups())
                .build();
    }
}
