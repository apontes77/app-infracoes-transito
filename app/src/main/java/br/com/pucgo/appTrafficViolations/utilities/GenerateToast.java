package br.com.pucgo.appTrafficViolations.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * classe utilitária para criar Toast.
 */
public class GenerateToast {

    public static void createShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void createLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
