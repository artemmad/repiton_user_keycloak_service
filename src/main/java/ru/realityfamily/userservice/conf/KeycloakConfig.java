package ru.realityfamily.userservice.conf;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class KeycloakConfig {

    static Keycloak keycloak = null;
    final static String serverUrl = "http://localhost:8085/auth"; //ссылку от корня еще до контроллера auth всегда, я не ебу почему, но они это считают корнем своего проекта ¯\_(ツ)_/¯
    public final static String realm = "repiton";
    final static String clientId = "admin-cli";
    final static String clientSecret = "r486NQgMRmlcJyzwYXH6aQxww52uvery";
//    final static String userName = "admin";
//    final static String password = "admin";

    public KeycloakConfig() {
    }

    public static Keycloak getInstance() {
        if (keycloak == null) {

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    //.username(userName)
                    //.password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
//                    .resteasyClient(new ResteasyClientBuilder()
//                            .connectionPoolSize(10)
//                            .build())
                    .build();
        }
        return keycloak;
    }
}