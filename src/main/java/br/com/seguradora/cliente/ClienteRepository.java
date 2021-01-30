package br.com.seguradora.cliente;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

    @Query("{ 'cpf' : ?0}")
    Optional<Cliente> verificaCpfExisteCadastrado(String cpf);
}
