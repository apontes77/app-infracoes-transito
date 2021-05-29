package br.com.pucgo.appInfracoes.modelos;

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
public class Denuncia {
    public String titulo;
    public String descricao;
    public String urlFoto;
}
