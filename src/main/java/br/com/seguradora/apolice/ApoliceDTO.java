package br.com.seguradora.apolice;

import br.com.seguradora.cliente.ClienteDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ApoliceDTO {
    @ApiModelProperty("Numero da apolice")
    private Long numeroApolice;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    @ApiModelProperty("Data Inicio da vigencia")
    private LocalDate dataInicioVigencia;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    @ApiModelProperty("Data fim da vigencia")
    private LocalDate dataFimVigencia;

    @NotEmpty
    @ApiModelProperty("Placa do veiculo")
    @Size(max = 7)
    private String placaVeiculo;

    @ApiModelProperty("Valor da apolice")
    @NotNull
    private BigDecimal valor;

    @NotNull
    @ApiModelProperty("Cliente da apolice")
    private ClienteDTO cliente;

    @ApiModelProperty("Situação da vigencia")
    private String vigencia;

    @ApiModelProperty("Indica quantos dias falta para vencer a apolice")
    private Long diasParaVencimento;
}