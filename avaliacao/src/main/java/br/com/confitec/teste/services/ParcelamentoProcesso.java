package br.com.confitec.teste.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.confitec.teste.interfaces.ParcelamentoInterface;
import br.com.confitec.teste.model.Cobertura;
import br.com.confitec.teste.model.Dados;
import br.com.confitec.teste.model.DadosDTO;
import br.com.confitec.teste.model.OpcaoParcelamento;
import br.com.confitec.teste.model.Parcelamento;

@Component
public class ParcelamentoProcesso implements ParcelamentoInterface {

    public DadosDTO execute(Parcelamento dto) {
        List<Dados> dadosList = new ArrayList<>();

        for (OpcaoParcelamento opcaoParcelamento : dto.getListOpcaoParcelamento()) {
            int minParcelas = opcaoParcelamento.getQuantidadeMinimaParcelas();
            int maxParcelas = opcaoParcelamento.getQuantidadeMaximaParcelas();

            for (int i = minParcelas; i <= maxParcelas; i++) {
                Dados dados = calcularParcelamento(dto, opcaoParcelamento, i);
                dadosList.add(dados);
            }
        }

        return new DadosDTO(dadosList);
    }

    private Dados calcularParcelamento(Parcelamento dto, OpcaoParcelamento opcao, int qtdParcelas) {
        Double valorParcelamentoTotal = calcularValorParcelamentoTotal(dto, opcao.getJuros(), qtdParcelas);
        Double valorPrimeiraParcela = calcularPrimeiraParcela(valorParcelamentoTotal, qtdParcelas);
        Double valorDemaisParcelas = calcularDemaisParcelas(valorParcelamentoTotal, qtdParcelas);

        Dados dados = new Dados();
        dados.setQuantidadeParcelas(qtdParcelas);
        dados.setValorPrimeiraParcela(arredondamento(valorPrimeiraParcela));
        dados.setValorDemaisParcelas(valorDemaisParcelas);
        dados.setValorParcelamentoTotal(arredondamento(valorParcelamentoTotal));

        return dados;
    }

    private Double calcularValorParcelamentoTotal(Parcelamento dto, Double juros, int qtdParcelas) {
        Double valorTotalCoberturas = calcularValorTotalCoberturas(dto.getListCobertura());
        return (juros.equals(0.0)) ? valorTotalCoberturas : calcularValorASerPago(valorTotalCoberturas, juros, qtdParcelas);
    }

    private Double calcularValorTotalCoberturas(List<Cobertura> listCobertura) {
        return listCobertura.stream().mapToDouble(Cobertura::getValor).sum();
    }

    private Double calcularValorASerPago(Double valorTotal, Double taxa, int qtdParcelas) {
        return valorTotal * Math.pow(1 + taxa, qtdParcelas);
    }

    private Double calcularPrimeiraParcela(Double valorASerPago, int qtdParcelas) {
        return valorASerPago / qtdParcelas;
    }

    private Double calcularDiferenca(Double valorASerPago, int qtdParcelas) {
        return valorASerPago - calcularResto(valorASerPago, qtdParcelas);
    }

    private Double calcularDemaisParcelas(Double valorASerPago, int qtdParcelas) {
        Double diferenca = calcularDiferenca(valorASerPago, qtdParcelas);
        return diferenca / qtdParcelas;
    }

    private Double calcularResto(Double valorASerPago, int qtdParcelas) {
        return valorASerPago % qtdParcelas;
    }

    private Double arredondamento(double valor) {
        return Math.round(valor * 100.0) / 100.0d;
    }
}
