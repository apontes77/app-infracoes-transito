package br.com.pucgo.appTrafficViolations.models;

import java.time.LocalDate;

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
    private String title;
    private String description;
    private LocalDate dateOfOccurrenceInfraction;
    private Double violationDistance;
    private Double proposalAmountTrafficTicket;
}
