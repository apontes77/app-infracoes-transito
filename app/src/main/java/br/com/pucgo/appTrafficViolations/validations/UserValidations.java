package br.com.pucgo.appTrafficViolations.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidations {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN ="^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern patternEmail = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern patternPassword = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email){
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String passwordInserted, String passwordRepeated) {
        Matcher matcher = null;
        if(passwordInserted.equals(passwordRepeated)) {
            matcher = patternPassword.matcher(passwordInserted);
        }
        return matcher.matches();
    }

    public static boolean validatePassword(String passwordInserted) {
        Matcher matcher = patternPassword.matcher(passwordInserted);
        return matcher.matches();
    }
}
