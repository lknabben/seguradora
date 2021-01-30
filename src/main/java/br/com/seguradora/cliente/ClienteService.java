package br.com.seguradora.cliente;

import br.com.seguradora.comum.GetMessageProperties;
import br.com.seguradora.comum.UtilsMapper;
import br.com.seguradora.exception.EntidadeNaoEncontradaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private GetMessageProperties messageProperties;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,
                          GetMessageProperties messageProperties) {
            this.clienteRepository = clienteRepository;
            this.messageProperties = messageProperties;
    }

    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream().map(r ->
                new ClienteDTO(r.getId(), r.getNome(),
                        r.getCpf(), r.getCidade(), r.getUf())).collect(Collectors.toList());
    }
    
    public Cliente buscarClientePorId(String id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(messageProperties.getMensagem("cliente.nao-localizado")));
    }
    
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {
        ModelMapper mapper = new ModelMapper();
        verificarSeCpfExisteCadastrado(clienteDTO.getCpf());
        Cliente cliente =  mapper.map(clienteDTO, Cliente.class);
        return mapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    public void deletarCliente(String id) {
        verificaClienteExistentePorId(id);
        clienteRepository.deleteById(id);
    }

    public Cliente verificaClienteExistentePorId(String id) {
        return clienteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(messageProperties.getMensagem("cliente.nao-localizado")));
    }

    public ClienteDTO atualizarCliente(ClienteDTO cliente) {
        Cliente clienteLocalizado = verificaClienteExistentePorId(cliente.getId());
        if (!cliente.getCpf().equals(clienteLocalizado.getCpf())) {
            verificarSeCpfExisteCadastrado(cliente.getCpf());
        }
        Cliente clienteAtualizado = clienteRepository.save(UtilsMapper.obterMapper().map(cliente, Cliente.class));
        return UtilsMapper.obterMapper().map(clienteAtualizado, ClienteDTO.class);
    }

    public Cliente verificarSeCpfExisteCadastrado(String cpf) {
        Optional<Cliente> cliente = clienteRepository.verificaCpfExisteCadastrado(cpf);
        if(cliente.isPresent()) {
            throw new ClienteException(messageProperties.getMensagem("cliente.cpf.existente"));
        }
        return cliente.orElse(null);
    }
}
