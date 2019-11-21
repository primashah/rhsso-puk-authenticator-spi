package rhsso.authenticator;

import com.google.common.flogger.FluentLogger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class PUKAuthenticatorFactory  implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "puk-authenticator";
    private static final PUKAuthenticator SINGLETON = new PUKAuthenticator();
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {


        return true;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName(PUKConstants.CONFIG_PUK_CODE_LENGTH);
        property.setLabel("Length of the PUK code");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("PUK Code Length");
        configProperties.add(property);

        property = new ProviderConfigProperty();
        property.setName(PUKConstants.CONFIG_PUK_NO_OF_INPUTS);
        property.setLabel("NO of PUK inputs");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("set how many inputs to allow user to enter");
        configProperties.add(property);


    }


    @Override
    public String getHelpText() {
        return "PUK";
    }

    @Override
    public String getDisplayType() {
        return "PUK Flow";
    }

    @Override
    public String getReferenceCategory() {
        return "PUK";
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }
}