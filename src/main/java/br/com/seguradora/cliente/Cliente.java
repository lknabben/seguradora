package br.com.seguradora.cliente;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Field("nome")
    private String nome;
    @Field("cpf")
    private String cpf;
    @Field("cidade")
    private String cidade;
    @Field("uf")
    private String uf;
}
