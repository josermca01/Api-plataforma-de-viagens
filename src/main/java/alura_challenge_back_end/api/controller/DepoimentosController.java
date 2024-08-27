package alura_challenge_back_end.api.controller;

import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import alura_challenge_back_end.api.entities.Depoimentos;
import alura_challenge_back_end.api.service.DepoimentosService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class DepoimentosController {

    @Autowired
    private DepoimentosService service;


    @GetMapping("/depoimentos")
    public ResponseEntity<Page<Depoimentos>> getDepoimentos(
        @RequestParam(value = "page",defaultValue = "0")Integer page,
        @RequestParam(value = "linesPerPage",defaultValue = "3")Integer linesPerPage,
        @RequestParam(value = "direction",defaultValue = "ASC")String direction,
        @RequestParam(value = "orderBy",defaultValue = "id")String orderBy){
            
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        Page <Depoimentos> list = service.getDepoimento(pageRequest);
        return ResponseEntity.ok().body(list);

    }
    @GetMapping("/depoimentos-home")
    public ResponseEntity<List<Depoimentos>> getDepoimentosAleatorios(){
        List<Depoimentos> list = service.getDepoimentoAleatorio();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/depoimentos/{id}")
	public ResponseEntity<Depoimentos> getDepoimentosById(@PathVariable Long id){
		Depoimentos dto = service.getDepoimentosById(id); 
		return ResponseEntity.ok().body(dto);
	}

    @PostMapping("/depoimentos")
	public ResponseEntity<Depoimentos> insert(@RequestBody Depoimentos insert){
        Depoimentos dto = service.insert(insert);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("/depoimentos/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

    @PutMapping("/depoimentos/{id}")
	public ResponseEntity<Depoimentos> update(@RequestBody Depoimentos update, @PathVariable Long id){
		Depoimentos dto = service.update(id, update); 
		return ResponseEntity.ok().body(dto);
	}
}
