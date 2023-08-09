package br.com.confitec.teste.model;

import java.util.List;

import lombok.Data;

@Data
public class Parcelamento {

    private List<Cobertura> listCobertura;
    private List<OpcaoParcelamento> listOpcaoParcelamento;
}