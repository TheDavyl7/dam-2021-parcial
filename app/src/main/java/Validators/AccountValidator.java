package Validators;

import java.util.regex.Pattern;

public class AccountValidator {

    public static boolean check_nom(String nom){
        return patternIsValid(nom, "^[a-z]{3,7}$");
    }



    public static boolean check_aposta(String aposta){
        return patternIsValid(aposta, "^(1000|[5-9]|[1-9][0-9][0-9]?)$");
    }


    private static boolean patternIsValid(String entrada, String patro){
        return Pattern.matches(patro,entrada);
    }
}
