# Local development

Dependencies:
- KeyCloak 16.1.1 (version in most Helm packages)

For local development, it is enough to raise a local keycloak instance for yourself. You can do it like this:

``
docker run -p 8085:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin quay.io/keycloak/keycloak:16.1.1
``

So the minimum version for work will rise to port 8085.
Next, you need to create a new Realm named **"repiton"**, go into it, find a Client named **"admin_cli"** and give him permissions to manipulate user accounts in the Service Account panel.
Minimal permissions are required for:
- selection of users
- creating users

Everything else is optional, but desirable.
After that, in the Credentials tab, you need to copy the secret of this user and paste it along with his client_id
into KeycloakConfig.java .
After all the manipulations with the credits, do not forget to change or clarify the address of the north if you changed the ports in the command above with the docker. If nothing has changed, it will start exactly with what is already in the config, with the exception of the user's secret, which will definitely have to be changed since it was recreated when the container started on your machine.