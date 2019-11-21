package rhsso.authenticator;

import org.keycloak.models.UserModel;

import java.util.Random;

public class Utils {

    public static String getCode(int codeLength){
        double maxValue = Math.pow(10.0, codeLength);
        Random r = new Random();
        long code = (long) (r.nextFloat() * maxValue);

        return String.valueOf(1234567);

    }
    public static String getUserVerificationCode(UserModel user){
        return user.getAttribute(PUKConstants.ATTR_PUK_CODE).isEmpty()? null:  user.getAttribute(PUKConstants.ATTR_PUK_CODE).get(0);
    }
    // using StringBuilder.append()
    public static String convertArrayToStringMethod(String[] strArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            stringBuilder.append(strArray[i]);
        }
        return stringBuilder.toString();
    }
}
