package br.com.pucgo.appInfracoes.modelos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private String login;
    private String senha;
}
