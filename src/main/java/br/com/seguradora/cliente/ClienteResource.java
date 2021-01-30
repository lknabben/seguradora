package br.com.seguradora.cliente;

import br.com.seguradora.comum.UtilsMapper;
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

@RequestMapping("/api/v1/clientes")
@RestController
public class ClienteResource {

    private ClienteService clienteService;

    @Autowired
    public ClienteResource(ClienteService clienteService) {
            this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable String id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        ClienteDTO clienteLocalizado = UtilsMapper.obterMapper().map(cliente, ClienteDTO.class);
        return ResponseEntity.ok(clienteLocalizado);
    }
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteDTO cliente = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.status(CREATED).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable String id, @RequestBody @Valid ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        ClienteDTO clienteAtualizado = clienteService.atualizarCliente(clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletarCliente(@PathVariable String id) {
        clienteService.deletarCliente(id);
    }
}
