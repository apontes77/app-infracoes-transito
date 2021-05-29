package br.com.pucgo.appInfracoes.modelos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String login;
    private String senha;
}
