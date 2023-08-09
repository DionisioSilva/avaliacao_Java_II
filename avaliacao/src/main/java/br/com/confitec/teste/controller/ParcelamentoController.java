package br.com.confitec.teste.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.confitec.teste.interfaces.ParcelamentoInterface;
import br.com.confitec.teste.model.DadosDTO;
import br.com.confitec.teste.model.Parcelamento;

@RequiredArgsConstructor
@RestController("/confitec/teste")
public class ParcelamentoController {

    private final ParcelamentoInterface parcelamentoProcesso;

    @PostMapping("parcelamento")
    public ResponseEntity<DadosDTO> parcelamento(@RequestBody Parcelamento parcelamento) {
        return new ResponseEntity<>(parcelamentoProcesso.execute(parcelamento), HttpStatus.OK);
    }
}
