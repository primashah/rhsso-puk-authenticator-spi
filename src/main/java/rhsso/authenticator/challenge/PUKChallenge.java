package rhsso.authenticator.challenge;

import com.google.common.flogger.FluentLogger;
import org.keycloak.authentication.AuthenticationFlowContext;
import rhsso.authenticator.PUKConstants;
import rhsso.authenticator.Utils;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.*;


public class PUKChallenge {
    private ArrayList<Integer> inputArray;
    private AuthenticationFlowContext authContext;
    private int inputLength;
    private int noOfInputs;
    private int firstUnMaskedInput;
    private HashMap<Integer,Integer> userEnteredValues;
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    public static final String PUKInputForm = "puk-input.ftl";

    public PUKChallenge(int inputLength, int noOfInputs, AuthenticationFlowContext context){
        this.inputArray = new ArrayList<Integer>(inputLength);
        this.authContext = context;
        this.inputLength = inputLength;
        this.noOfInputs = noOfInputs;

        this.setInputArray();
    }
    public PUKChallenge(){}

    public PUKChallenge setAuthContext(AuthenticationFlowContext authContext) {
        this.authContext = authContext;
        return this;
    }
    public PUKChallenge setInputLength(int inputLength) {
        this.inputLength = inputLength;
        return this;
    }
    public PUKChallenge setNoOfInputs(int noOfInputs) {
        this.noOfInputs = noOfInputs;
        return this;
    }


    private void setInputArray(){
        int uniqueOccurrences = 0;
        ArrayList<Integer> tempArray = new ArrayList<>(this.inputLength);
        ArrayList<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < this.inputLength; i++) {
            tempArray.add(i,i);
            this.inputArray.add(i,0);
            newList.add(i, -1);
        }
        Random rand = new Random();
        for (int i = uniqueOccurrences; uniqueOccurrences < this.noOfInputs; i++) {
         int randomIndex = rand.nextInt(this.inputLength) ;
           if(!newList.contains(randomIndex)) {

                this.inputArray.set(randomIndex,1);
                newList.add(uniqueOccurrences,randomIndex);
               uniqueOccurrences++;
               // System.out.println("random number between 0 and  " + tempArray.size() + " : " + (randomIndex + 1) );
            }
        }
        this.firstUnMaskedInput = this.inputArray.indexOf(1) + 1;
       // System.out.println("firstUnMaskedInput" + (this.inputArray.lastIndexOf(1) + 1) );

    }


    public Response getChallenge(){
        Response challenge = this.authContext.form()
                .setAttribute("inputLength", this.inputLength)
                .setAttribute("inputArray", this.inputArray)
                .setAttribute("firstUnMaskedInput", this.firstUnMaskedInput)
                .createForm(PUKInputForm);
        return challenge;
    }
    public Response getErrorChallenge(){
        Response challenge = this.authContext.form()
                .setError(PUKConstants.INVALID_PUK_CODE_MESSAGE)
                .setAttribute("inputLength", 0)
                .setAttribute("inputArray",new ArrayList<Integer>())
                .setAttribute("firstUnMaskedInput", 0)
                .createForm(PUKInputForm);
        return challenge;
    }
    public ArrayList<Integer> getInputArray(){
        return this.inputArray;
    }
    public PUKChallenge save(){
        this.authContext.getUser().setAttribute(PUKConstants.ATTR_PUK_CODE, Arrays.asList(Utils.getCode(this.inputLength)));
        return this;
    }

    public HashMap<Integer,Integer> getUserEnteredValues (){
        return this.userEnteredValues;
    }

    private void setUserEnteredValues(HashMap<Integer,Integer> values){
        this.userEnteredValues = values;
    }
    public Boolean isValid(MultivaluedMap<String, String> formData){

        Set<String> keys = formData.keySet();
        HashMap<Integer,Integer> userEnteredValues = new HashMap<>();

        String pukCode = Utils.getUserVerificationCode(this.authContext.getUser());
        String[] codeInputArray = new String[this.inputLength];
        Arrays.fill(codeInputArray,"-1");

        Integer PUKSequenceNo ;
        String userEnteredCode;

        // iterate through the key set and display key and values
        for (String key : keys) {
                if(key.startsWith(PUKConstants.PREFIX_CODE_INPUT)){
                     PUKSequenceNo =  Integer.valueOf(key.substring(PUKConstants.PREFIX_CODE_INPUT.length()));
                     userEnteredCode = formData.getFirst(key);
                    logger.atInfo().log("userEnteredCode " + userEnteredCode);

                     codeInputArray[PUKSequenceNo -1] = userEnteredCode != null && !userEnteredCode.isEmpty()? userEnteredCode : pukCode.substring(PUKSequenceNo -1,PUKSequenceNo);
                     userEnteredValues.put(PUKSequenceNo -1,Integer.valueOf(codeInputArray[PUKSequenceNo -1]));
                 }
        }

        this.userEnteredValues = userEnteredValues;
        userEnteredCode = Utils.convertArrayToStringMethod(codeInputArray);
        logger.atInfo().log("userEnteredCode" + userEnteredCode);
        logger.atInfo().log("pukCode" + pukCode);
            return userEnteredCode.equals(pukCode);
    }








}
