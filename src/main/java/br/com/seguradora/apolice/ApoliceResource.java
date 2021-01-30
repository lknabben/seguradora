package br.com.seguradora.apolice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/apolice")
@RestController
public class ApoliceResource {
    private ApoliceService apoliceService;

    @Autowired
    public ApoliceResource(ApoliceService apoliceService) {
            this.apoliceService = apoliceService;
    }

    @PostMapping
    public ResponseEntity<ApoliceDTO> cadastrarApolice(@RequestBody @Valid ApoliceDTO apoliceDTO) {
        ApoliceDTO apoliceCriada = apoliceService.cadastrarApolice(apoliceDTO);
        return ResponseEntity.status(CREATED).body(apoliceCriada);
    }

    @GetMapping
    public ResponseEntity<List<ApoliceDTO>> listarApolices() {
        return ResponseEntity.ok(apoliceService.listarApolices());
    }

    @GetMapping("/{numeroApolice}")
    public ResponseEntity<ApoliceDTO> buscarApolicePorNumero(@PathVariable Long numeroApolice) {
        ApoliceDTO apolice = apoliceService.recuperarEverificarApolice(numeroApolice);
        return ResponseEntity.status(OK).body(apolice);
    }

    @PutMapping("/{numeroApolice}")
    public ResponseEntity<ApoliceDTO> atualizarApolice(@PathVariable Long numeroApolice, @RequestBody @Valid ApoliceDTO apoliceDTO) {
        apoliceDTO.setNumeroApolice(numeroApolice);
        ApoliceDTO apoliceAtualizada = apoliceService.atualizarApolice(apoliceDTO);
        return ResponseEntity.ok(apoliceAtualizada);
    }

    @DeleteMapping("/{numeroApolice}")
    @ResponseStatus(NO_CONTENT)
    public void deletarApolicePorNumero(@PathVariable Long numeroApolice) {
        apoliceService.deletarApolicePorNumero(numeroApolice);
    }
}
