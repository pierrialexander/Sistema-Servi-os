package com.api.servico.backend.controller;


import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value="/api/servicos")
public class ServicoController {
    
    @Autowired
    private ServicoService servicoService;
    
	@GetMapping
	public ResponseEntity<List<Servico>> findAll() {
		 List<Servico> list = servicoService.buscarTodos();
		 return ResponseEntity.ok().body(list);
	}

    @GetMapping(value="/{id}")
    public ResponseEntity<Servico> findById(@PathVariable Long id) {
        Servico obj = servicoService.buscarPorId(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value="/pagamentopendente")
    public ResponseEntity<List<Servico>> buscarServicosPagamentoPendente() {
    	List<Servico> list = servicoService.buscarServicosPagamentoPendente();
    	return ResponseEntity.ok().body(list);
    }

    @GetMapping(value="/cancelados")
    public ResponseEntity<List<Servico>> buscarServicosCancelados() {
        List<Servico> list = servicoService.buscarServicosCancelados();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Servico> insert(@RequestBody Servico obj) {
        obj = servicoService.inserir(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
    
    @PutMapping
    public Servico update(@RequestBody Servico obj) {
    	return servicoService.alterar(obj);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
    	servicoService.excluir(id);
    	return ResponseEntity.noContent().build();
    }
}
