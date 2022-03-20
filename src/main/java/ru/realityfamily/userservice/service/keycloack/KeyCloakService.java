package ru.realityfamily.userservice.service.keycloack;

import lombok.AllArgsConstructor;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.realityfamily.userservice.conf.KeycloakConfig;
import ru.realityfamily.userservice.service.keycloack.entity.Credentials;
import ru.realityfamily.userservice.web.dto.UserRequest;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class KeyCloakService {


    public UserRepresentation addUser(UserRequest userDTO) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));
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

    public List<org.keycloak.representations.idm.RoleRepresentation> getRolesAll() {
        RolesResource rolesResource = getInstanceRolesResource();
        return rolesResource.list();
    }


    public UserRepresentation updateUser(String userId, UserRequest userDTO) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstname());
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

    public UsersResource getInstanceUsersResource() {
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    }


    public RolesResource getInstanceRolesResource() {
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).roles();
    }


}
