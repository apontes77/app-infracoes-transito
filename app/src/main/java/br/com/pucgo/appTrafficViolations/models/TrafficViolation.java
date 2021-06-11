package br.com.pucgo.appTrafficViolations.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class TrafficViolation {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("photo")
    private String photo;
    @SerializedName("dateOfOccurrenceInfraction")
    private Date dateOfOccurrenceInfraction;
    @SerializedName("violationDistance")
    private Double violationDistance;
    @SerializedName("proposalAmountTrafficTicket")
    private Double proposalAmountTrafficTicket;
}
