package br.com.confitec.teste.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dados {

    private Integer quantidadeParcelas;
    private Double valorPrimeiraParcela;
    private Double valorDemaisParcelas;
    private Double valorParcelamentoTotal;
}