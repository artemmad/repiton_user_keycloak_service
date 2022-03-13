package ru.realityfamily.userservice.conf.swagger.codegen;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class GeneratedApiBootstrapConf {
//
//    @Autowired
//    AttackDetectionApi attackDetectionApi;
//
//    @Autowired
//    AuthenticationManagementApi authenticationManagementApi;
//
//    @Autowired
//    ClientAttributeCertificateApi clientAttributeCertificateApi;
//
//    @Autowired
//    ClientInitialAccessApi clientInitialAccessApi;
//
//    @Autowired
//    ClientRegistrationPolicyApi clientRegistrationPolicyApi;
//
//    @Autowired
//    ClientRoleMappingsApi clientRoleMappingsApi;
//
//    @Autowired
//    ClientsApi clientsApi;
//
//    @Autowired
//    ClientScopesApi clientScopesApi;
//
//    @Autowired
//    ComponentApi componentApi;
//
//    @Autowired
//    GroupsApi groupsApi;
//
//    @Autowired
//    IdentityProvidersApi identityProvidersApi;
//
//    @Autowired
//    KeyApi keyApi;
//
//    @Autowired
//    ProtocolMappersApi protocolMappersApi;
//
//    @Autowired
//    RealmsAdminApi realmsAdminApi;
//
//    @Autowired
//    RoleMapperApi roleMapperApi;
//
//    @Autowired
//    RolesApi rolesApi;
//
//    @Autowired
//    RolesByIdApi rolesByIdApi;
//
//    @Autowired
//    RootApi rootApi;
//
//    @Autowired
//    ScopeMappingsApi scopeMappingsApi;
//
//    @Autowired
//    UsersApi usersApi;
//
//    @Autowired
//    UserStorageProviderApi userStorageProviderApi;


//    @Value("${keycloak.api.url}")
//    private String keycloakUrl;
//
//
//    @Bean
////    @Qualifier("correct")
//    public AttackDetectionApi getAttackDetectionInterface() {
//        AttackDetectionApi attackDetectionApi = new AttackDetectionApi();
//        attackDetectionApi.getApiClient().setBasePath(keycloakUrl);
//        return attackDetectionApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public AuthenticationManagementApi getAuthenticationManagmentInterface() {
//        AuthenticationManagementApi authenticationManagementApi = new AuthenticationManagementApi();
//        authenticationManagementApi.getApiClient().setBasePath(keycloakUrl);
//        return authenticationManagementApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ClientAttributeCertificateApi getClientAttributeCertificateInterface() {
//        ClientAttributeCertificateApi clientAttributeCertificateApi = new ClientAttributeCertificateApi();
//        clientAttributeCertificateApi.getApiClient().setBasePath(keycloakUrl);
//        return clientAttributeCertificateApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ClientInitialAccessApi getClientInitialAccessInterface() {
//        ClientInitialAccessApi clientInitialAccessApi = new ClientInitialAccessApi();
//        clientInitialAccessApi.getApiClient().setBasePath(keycloakUrl);
//        return clientInitialAccessApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ClientRegistrationPolicyApi getClientRegistrationPolicyInterface() {
//        ClientRegistrationPolicyApi clientRegistrationPolicyApi = new ClientRegistrationPolicyApi();
//        clientRegistrationPolicyApi.getApiClient().setBasePath(keycloakUrl);
//        return clientRegistrationPolicyApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ClientRoleMappingsApi getClientRoleMappingsInterface() {
//        ClientRoleMappingsApi clientRoleMappingsApi = new ClientRoleMappingsApi();
//        clientRoleMappingsApi.getApiClient().setBasePath(keycloakUrl);
//        return clientRoleMappingsApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ClientsApi getClientsInterface() {
//        ClientsApi clientsApi = new ClientsApi();
//        clientsApi.getApiClient().setBasePath(keycloakUrl);
//        return clientsApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ClientScopesApi getClientScopesInterface() {
//        ClientScopesApi clientScopesApi = new ClientScopesApi();
//        clientScopesApi.getApiClient().setBasePath(keycloakUrl);
//        return clientScopesApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public ComponentApi getComponentInterface() {
//        ComponentApi componentApi = new ComponentApi();
//        componentApi.getApiClient().setBasePath(keycloakUrl);
//        return componentApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public GroupsApi getGroupsInterface() {
//        GroupsApi groupsApi = new GroupsApi();
//        groupsApi.getApiClient().setBasePath(keycloakUrl);
//        return groupsApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public IdentityProvidersApi getIdentityProvidersInterface() {
//        IdentityProvidersApi identityProvidersApi = new IdentityProvidersApi();
//        identityProvidersApi.getApiClient().setBasePath(keycloakUrl);
//        return identityProvidersApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public KeyApi getKeyInterface() {
//        KeyApi keyApi = new KeyApi();
//        keyApi.getApiClient().setBasePath(keycloakUrl);
//        return keyApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public ProtocolMappersApi getProtocolMappersInterface() {
//        ProtocolMappersApi protocolMappersApi = new ProtocolMappersApi();
//        protocolMappersApi.getApiClient().setBasePath(keycloakUrl);
//        return protocolMappersApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public RealmsAdminApi getRealmsAdminInterface() {
//        RealmsAdminApi realmsAdminApi = new RealmsAdminApi();
//        realmsAdminApi.getApiClient().setBasePath(keycloakUrl);
//        return realmsAdminApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public RoleMapperApi getRoleMapperInterface() {
//        RoleMapperApi roleMapperApi = new RoleMapperApi();
//        roleMapperApi.getApiClient().setBasePath(keycloakUrl);
//        return roleMapperApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public RolesApi getRolesInterface() {
//        RolesApi rolesApi = new RolesApi();
//        rolesApi.getApiClient().setBasePath(keycloakUrl);
//        return rolesApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public RolesByIdApi getRolesByIdInterface() {
//        RolesByIdApi rolesByIdApi = new RolesByIdApi();
//        rolesByIdApi.getApiClient().setBasePath(keycloakUrl);
//        return rolesByIdApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public RootApi getRootInterface() {
//        RootApi rootApi = new RootApi();
//        rootApi.getApiClient().setBasePath(keycloakUrl);
//        return rootApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public ScopeMappingsApi getScopeMappingsInterface() {
//        ScopeMappingsApi scopeMappingsApi = new ScopeMappingsApi();
//        scopeMappingsApi.getApiClient().setBasePath(keycloakUrl);
//        return scopeMappingsApi;
//    }
//
//
//    @Bean
////    @Qualifier("correct")
//    public UsersApi getUsersInterface() {
//        UsersApi usersApi = new UsersApi();
//        usersApi.getApiClient().setBasePath(keycloakUrl);
//        return usersApi;
//    }
//
//    @Bean
////    @Qualifier("correct")
//    public UserStorageProviderApi getUserStorageProviderInterface() {
//        UserStorageProviderApi userStorageProviderApi = new UserStorageProviderApi();
//        userStorageProviderApi.getApiClient().setBasePath(keycloakUrl);
//        return userStorageProviderApi;
//    }
//

}
