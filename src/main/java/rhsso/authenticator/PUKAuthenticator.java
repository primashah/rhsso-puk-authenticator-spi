package rhsso.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;

import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import rhsso.authenticator.challenge.PUKChallenge;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class PUKAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {

        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        PUKChallenge pukChallenge = new PUKChallenge(Integer.valueOf(config.getConfig().get(PUKConstants.CONFIG_PUK_CODE_LENGTH)),Integer.valueOf(config.getConfig().get(PUKConstants.CONFIG_PUK_NO_OF_INPUTS)),context);

        Response challenge = pukChallenge.save().getChallenge();
        context.challenge(challenge);
    }










    @Override
    public void action(AuthenticationFlowContext context) {

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        if (formData.containsKey("cancel")) {
            context.resetFlow();
            return;
        }

        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        PUKChallenge challenge = new PUKChallenge(Integer.valueOf(config.getConfig().get(PUKConstants.CONFIG_PUK_CODE_LENGTH)),Integer.valueOf(config.getConfig().get(PUKConstants.CONFIG_PUK_NO_OF_INPUTS)),context);

        if(challenge.isValid(formData)){
            context.success();
            return;

        }
        context.challenge(challenge.getErrorChallenge());

    }


    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {

    }
}