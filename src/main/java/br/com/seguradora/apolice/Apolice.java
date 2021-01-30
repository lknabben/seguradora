package br.com.seguradora.apolice;

import br.com.seguradora.cliente.Cliente;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document("apolice")
public class Apolice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @DBRef
    private Cliente cliente;
    @Field("numeroApolice")
    private Long numeroApolice;
    @Field("dataInicioVigencia")
    private LocalDate dataInicioVigencia;
    @Field("dataFimVigencia")
    private LocalDate dataFimVigencia;
    @Field("placaVeiculo")
    private String placaVeiculo;
    @Field("valor")
    private BigDecimal valor;
}
