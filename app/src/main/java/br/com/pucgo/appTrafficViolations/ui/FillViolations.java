package br.com.pucgo.appTrafficViolations.ui;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.pucgo.appTrafficViolations.models.TrafficViolation;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiClient;
import br.com.pucgo.appTrafficViolations.retrofit.RestApiInterfaceTrafficViolation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FillViolations {
    private static final String TAG = "THIS";
    RestApiInterfaceTrafficViolation apiServiceViolation =  RestApiClient
                                                                        .getClient()
                                                                        .create(RestApiInterfaceTrafficViolation.class);

    private static List<TrafficViolation> violationsList = new ArrayList<TrafficViolation>();

    public static List<TrafficViolation> getViolationsList() {
        return violationsList;
    }

    public static void setViolationsList(List<TrafficViolation> violationsList) {
        FillViolations.violationsList = violationsList;
    }

    public FillViolations() {
        violations();
    }

    private void violations() {
    }
}
