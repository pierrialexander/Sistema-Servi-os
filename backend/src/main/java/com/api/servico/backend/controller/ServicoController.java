package com.api.servico.backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.servico.backend.entity.Servico;
import com.api.servico.backend.service.ServicoService;

@RestController
@RequestMapping(value="/servicos")
public class ServicoController {
    
    @Autowired
    private ServicoService servicoService;
    
	@GetMapping
	public ResponseEntity<List<Servico>> findAll() {
		 List<Servico> list = servicoService.buscarTodos();
		 return ResponseEntity.ok().body(list);
	}
}
