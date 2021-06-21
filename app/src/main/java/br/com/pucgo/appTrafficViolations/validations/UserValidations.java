package br.com.pucgo.appTrafficViolations.validations;

import android.content.Intent;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pucgo.appTrafficViolations.utilities.ErrorActivity;

/**
 * VALIDA UM EMAIL COM O FORMATO MINIMO SENDO: x@x.xx.
 *
 * VALIDA SENHA COM NO MINIMO OITO CARACTERES, CONTENDO PELO MENOS UMA LETRA MAIUSCULA, UMA MINUSCULA E UM NUMERO.
 */

public class UserValidations {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN ="^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern patternEmail = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern patternPassword = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email){
        Matcher matcher = null;
        try {
            matcher = patternEmail.matcher(email);
        } catch (Exception e) {
            Log.v("EXCEÇÃO_EMAIL:", ""+e.getMessage());
        }
        return matcher.matches();
    }

    public static boolean validatePassword(String passwordInserted, String passwordRepeated) {
        Matcher matcher = null;
        try {
            if(passwordInserted.equals(passwordRepeated)) {
                matcher = patternPassword.matcher(passwordInserted);
            }
        } catch (Exception e){
            Log.v("EXCEÇÃO_SENHA:", ""+e.getMessage());
        }

        return matcher.matches();
    }

    public static boolean validatePassword(String passwordInserted) {
        Matcher matcher = null;
        try {
            matcher = patternPassword.matcher(passwordInserted);
        } catch (Exception e) {
            Log.v("EXCEÇÃO_SENHA:", ""+e.getMessage());
        }
        return matcher.matches();
    }
}
