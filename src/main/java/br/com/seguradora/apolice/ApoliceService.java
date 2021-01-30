package br.com.seguradora.apolice;

import br.com.seguradora.cliente.Cliente;
import br.com.seguradora.cliente.ClienteDTO;
import br.com.seguradora.cliente.ClienteService;
import br.com.seguradora.comum.GetMessageProperties;
import br.com.seguradora.comum.UtilsMapper;
import br.com.seguradora.exception.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ApoliceService {
    private ApoliceRepository apoliceRepository;
    private ClienteService clienteService;
    private GetMessageProperties messageProperties;

    @Autowired
    public ApoliceService(ApoliceRepository apoliceRepository,
                          ClienteService clienteService,
                          GetMessageProperties messageProperties) {
        this.apoliceRepository = apoliceRepository;
        this.clienteService = clienteService;
        this.messageProperties = messageProperties;
    }

    public ApoliceDTO cadastrarApolice(ApoliceDTO apoliceDTO) {
        Cliente cliente = clienteService.verificaClienteExistentePorId(apoliceDTO.getCliente().getId());
        apoliceDTO.setNumeroApolice(gerarNumeroApoliceValido());
        Apolice apoliceSalva = apoliceRepository.save(UtilsMapper.obterMapper().map(apoliceDTO, Apolice.class));
        ApoliceDTO apoliceVerificada = verificaVigenciaApolice(apoliceSalva);
        apoliceVerificada.setCliente(UtilsMapper.obterMapper().map(cliente, ClienteDTO.class));
        return apoliceVerificada;
    }


    public ApoliceDTO verificaVigenciaApolice(Apolice apolice) {
        boolean emDIa = LocalDate.now().isBefore(apolice.getDataFimVigencia());
        ApoliceDTO apoliceDTO = UtilsMapper.obterMapper().map(apolice, ApoliceDTO.class);
        long diasVencidos = apolice.getDataFimVigencia().until(LocalDate.now(), ChronoUnit.DAYS);
        apoliceDTO.setVigencia("Apolice em dia");
        if (!emDIa) {
            apoliceDTO.setVigencia("Apolice vencida");
        }
        apoliceDTO.setDiasParaVencimento(diasVencidos);
        return apoliceDTO;
    }

    public List<ApoliceDTO> listarApolices() {
        List<Apolice> all = apoliceRepository.findAll();
        List<ApoliceDTO> lista = null;
        if(all.size() > 0) {
            lista = new ArrayList<>();
            for (Apolice apolice: all) {
                ApoliceDTO apoliceVerificada = verificaVigenciaApolice(apolice);
                lista.add(apoliceVerificada);
            }
        }
        return lista;
    }

    public ApoliceDTO atualizarApolice(ApoliceDTO apoliceDTO) {
        Apolice apoliceMapper = UtilsMapper.obterMapper().map(apoliceDTO, Apolice.class);
        Apolice apoliceLocalizada = buscarApolicePorNumero(apoliceDTO.getNumeroApolice());

        apoliceMapper.setId(apoliceLocalizada.getId());
        Apolice apoliceSalva = apoliceRepository.save(apoliceMapper);

        Cliente cliente = clienteService.buscarClientePorId(apoliceSalva.getCliente().getId());
        apoliceSalva.setCliente(cliente);
        return verificaVigenciaApolice(apoliceSalva);
    }

    public ApoliceDTO recuperarEverificarApolice(Long numeroApolice) {
        Apolice apolice = apoliceRepository.buscarApolicePorNumero(numeroApolice)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(messageProperties.getMensagem("apolice.nao-localizada")));

        Cliente cliente = clienteService.buscarClientePorId(apolice.getCliente().getId());
        apolice.setCliente(cliente);
        return verificaVigenciaApolice(apolice);
    }

    public Apolice buscarApolicePorNumero(Long numeroApolice) {
        return apoliceRepository.buscarApolicePorNumero(numeroApolice)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(messageProperties.getMensagem("apolice.nao-localizada")));
    }

    public void deletarApolicePorNumero(Long numeroApolice) {
        Apolice apolice = buscarApolicePorNumero(numeroApolice);
        apoliceRepository.deleteById(apolice.getId());
    }

    /**
     *  Utilizando lib math, mas teria como opção UUID, devido a estar especificando "numero" apolice,
     *  foi utilizado essa lib.
     */
    private Long gerarNumeroApoliceValido() {
        Random numero = new Random();
        long numeroGerado = 0L;
        while (numeroGerado == 0L) {
            long random = Math.abs(numero.nextLong() * 100);
            if (!apoliceRepository.buscarApolicePorNumero(random).isPresent()) {
                numeroGerado = random;
            }
        }
        return numeroGerado;
    }
}
