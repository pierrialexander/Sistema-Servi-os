package com.api.servico.backend.controller;


import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.servico.backend.entity.Servico;
import com.api.servico.backend.service.ServicoService;

/**
 * Controlador para operações relacionadas à entidade Servico.
 */
@RestController
@RequestMapping(value="/api/servicos")
public class ServicoController {
    
    @Autowired
    private ServicoService servicoService;
    
    /**
     * Recupera todos os serviços.
     *
     * @return ResponseEntity contendo a lista de todos os serviços.
     */
//    @GetMapping
//    @CrossOrigin(origins = "http://localhost:3000")
//    public ResponseEntity<List<Servico>> findAll() {
//        List<Servico> list = servicoService.buscarTodos();
//        return ResponseEntity.ok().body(list);
//    }
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<Servico>> findAll(Pageable pageable) {
        Page<Servico> list = servicoService.buscarTodos(pageable);
        return ResponseEntity.ok().body(list);
    }

    /**
     * Recupera um serviço pelo ID.
     *
     * @param id ID do serviço a ser buscado.
     * @return ResponseEntity contendo o serviço correspondente ao ID fornecido.
     */
    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Servico> findById(@PathVariable Long id) {
        Servico obj = servicoService.buscarPorId(id);
        return ResponseEntity.ok().body(obj);
    }

    /**
     * Recupera serviços com pagamento pendente.
     *
     * @return ResponseEntity contendo a lista de serviços com pagamento pendente.
     */
    @GetMapping(value = "/pagamentopendente")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Servico>> buscarServicosPagamentoPendente() {
        List<Servico> list = servicoService.buscarServicosPagamentoPendente();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Recupera serviços cancelados.
     *
     * @return ResponseEntity contendo a lista de serviços cancelados.
     */
    @GetMapping(value = "/cancelados")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Servico>> buscarServicosCancelados() {
        List<Servico> list = servicoService.buscarServicosCancelados();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Recupera serviços cancelados.
     *
     * @return ResponseEntity contendo a lista de serviços cancelados.
     */
    @GetMapping(value = "/realizados")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Servico>> buscarServicosRealizados() {
        List<Servico> list = servicoService.buscarServicosRealizados();
        return ResponseEntity.ok().body(list);
    }



    /**
     * Insere um novo serviço.
     *
     * @param obj Serviço a ser inserido.
     * @return ResponseEntity contendo o serviço inserido e a URI para acessar o recurso.
     */
    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Servico> insert(@RequestBody Servico obj) {
        obj = servicoService.inserir(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    /**
     * Atualiza um serviço existente.
     *
     * @param obj Serviço com as alterações a serem aplicadas.
     * @return O serviço atualizado.
     */
    @PutMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public Servico update(@RequestBody Servico obj) {
        return servicoService.alterar(obj);
    }

    /**
     * Exclui um serviço pelo ID.
     *
     * @param id ID do serviço a ser excluído.
     * @return ResponseEntity indicando o sucesso da operação.
     */
    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        servicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cancela um serviço pelo ID.
     *
     * @param id ID do serviço a ser cancelado.
     * @return ResponseEntity indicando o sucesso da operação.
     */
    @PostMapping(value = "/cancelarservico/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> cancelarServico(@PathVariable Long id) {
        servicoService.cancelarServico(id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Cancela um serviço pelo ID.
     *
     * @param id ID do serviço a ser cancelado.
     * @return ResponseEntity indicando o sucesso da operação.
     */
    @PostMapping(value = "/reativarservico/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> reativarServico(@PathVariable Long id) {
        servicoService.reativarServico(id);
        return ResponseEntity.ok().build();
    }
}
