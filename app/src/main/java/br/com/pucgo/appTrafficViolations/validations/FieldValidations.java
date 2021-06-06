package br.com.pucgo.appTrafficViolations.validations;

import android.widget.EditText;

public class FieldValidations {
    public void validaCamposVazios(EditText edt1) {
        if (edt1.getText().toString().equals("")) {
            edt1.setError("Este campo deve ser preenchido");
        }
    }
}
