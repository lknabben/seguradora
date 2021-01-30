package br.com.seguradora.cliente;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    @ApiModelProperty("ID do cliente")
    private String id;

    @NotEmpty
    @ApiModelProperty("Nome do cliente")
    private String nome;

    @NotEmpty
    @CPF
    @ApiModelProperty("CPF do cliente")
    private String cpf;

    @NotEmpty
    @ApiModelProperty("Cidade do cliente")
    private String cidade;

    @NotEmpty
    @ApiModelProperty("UF")
    private String uf;
}
