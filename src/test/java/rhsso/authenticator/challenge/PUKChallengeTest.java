package rhsso.authenticator.challenge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.forms.login.LoginFormsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PUKChallengeTest {
    private  static AuthenticationFlowContext context;
    private  static LoginFormsProvider form;


    @BeforeAll
    public static void setup(){
        form = createLoginFormsProvider();
        context = createAuthenticationContext();
        when(context.form()).thenReturn(form);



    }
        @Test
        public void instance(){
            PUKChallenge pukChallenge = new PUKChallenge(5,2,context);
            assertNotNull(pukChallenge);
        }

        @Test
        public void setInputArray(){
            PUKChallenge pukChallenge = new PUKChallenge(5,2,context);
            assertNotNull(pukChallenge.getInputArray(),"Input Array should not be null");
        }

        @Test
        public void inputArray10With3(){
            PUKChallenge pukChallenge = new PUKChallenge(10,3,context);

            assertTrue(getOccurrences(pukChallenge.getInputArray()) == 3,"Input Array should have occurences of value 'true' 3 times");
            assertTrue(pukChallenge.getInputArray().size() == 10,"Input Array should be initialised with 10 items");
        }

    @Test
    public void inputArray7With3(){
        PUKChallenge pukChallenge = new PUKChallenge(7,3,context);

        assertTrue(getOccurrences(pukChallenge.getInputArray())  == 3,"Input Array should have occurence of value 'true' 5 times");
       assertTrue(pukChallenge.getInputArray().size() == 7,"Input Array should be initialised with 10 items");
    }

    @Test
    public void inputArray20With8(){
        PUKChallenge pukChallenge = new PUKChallenge(20,8,context);

        assertTrue(getOccurrences(pukChallenge.getInputArray())  == 8,"Input Array should have occurence of value 'true' 8 times");
        assertTrue(pukChallenge.getInputArray().size() == 20,"Input Array should be initialised with 20 items");
    }
    private int getOccurrences(ArrayList<Integer> arr){
        int occurrence = 0;
        for (Integer item : arr) {
            if (item == 1) {

                occurrence++;

            }
        }
        return occurrence;
    }
    private static AuthenticationFlowContext createAuthenticationContext() {
        AuthenticationFlowContext mock = mock(AuthenticationFlowContext.class);


        return mock;
    }
    private static LoginFormsProvider createLoginFormsProvider() {
        LoginFormsProvider mock = mock(LoginFormsProvider.class);

        return mock;
    }
}
