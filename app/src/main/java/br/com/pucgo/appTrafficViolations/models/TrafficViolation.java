package br.com.pucgo.appTrafficViolations.models;

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
    public String title;
    public String description;
}
