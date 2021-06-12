package br.com.pucgo.appTrafficViolations.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * classe de modelo - usuário
 */
public class User {
    private String login;
    private String password;
    private String CPF;

    public User (String login, String password) {
        this.login = login;
        this.password = password;
    }
}
