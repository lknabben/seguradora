package br.com.seguradora.comum;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class UtilsMapper {

    public static ModelMapper obterMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
