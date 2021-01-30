package br.com.seguradora.apolice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ApoliceRepository extends MongoRepository<Apolice, String> {

    @Query("{ 'numeroApolice' : ?0}")
    Optional<Apolice> buscarApolicePorNumero(Long numeroApolice);
}
