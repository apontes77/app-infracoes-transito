package br.com.pucgo.appInfracoes.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacoesDeUsuario {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String SENHA_PATTERN ="^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern patternEmail = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern patternSenha = Pattern.compile(SENHA_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean validarEmail(String email){
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }

    public static boolean validarSenha(String senhaInserida, String senhaRepetida) {
        Matcher matcher = null;
        if(senhaInserida.equals(senhaRepetida)) {
            matcher = patternSenha.matcher(senhaInserida);
        }
        return matcher.matches();
    }

    public static boolean validarSenha(String senhaInserida) {
        Matcher matcher = patternSenha.matcher(senhaInserida);
        return matcher.matches();
    }
}
