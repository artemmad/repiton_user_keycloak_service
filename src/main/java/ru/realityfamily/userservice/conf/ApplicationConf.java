package ru.realityfamily.userservice.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationConf {
    @Value("${keycloak.auth-server-url}")
    private String serverUrl; //ссылку от корня еще до контроллера auth всегда, я не ебу почему, но они это считают корнем своего проекта ¯\_(ツ)_/¯
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
//    final static String userName = "admin";

    @Bean(name = "patchingMapper")
    ModelMapper patchingModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean(name = "defaultMapper")
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Bean
    public Keycloak keyCloak() {

        Keycloak keycloak = KeycloakBuilder.builder()
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
        return keycloak;
    }

    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Server serverThrowGateway = new Server();
        serverThrowGateway.setUrl("http://localhost:9101/userservice");
        Server serverWithoutGateway = new Server();
        serverWithoutGateway.setUrl("http://localhost:9100/userservice");
        Server serverLabaK8s = new Server();
        serverWithoutGateway.setUrl("https://backend.repiton.dev.realityfamily.ru:9046/userservice/");
        return new OpenAPI().servers(List.of(serverThrowGateway, serverWithoutGateway));
    }

}
