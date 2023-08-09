package br.com.confitec.teste.model;

import lombok.Data;

@Data
public class OpcaoParcelamento {

    private Integer quantidadeMinimaParcelas;
    private Integer quantidadeMaximaParcelas;
    private Double juros;
}