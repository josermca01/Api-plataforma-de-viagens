package alura_challenge_back_end.api.controller;

import java.net.URI;

import java.util.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import alura_challenge_back_end.api.entities.Perfil;
import alura_challenge_back_end.api.entities.PerfilDTO;
import alura_challenge_back_end.api.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    public ResponseEntity<List<Perfil>> download(@RequestParam(value = "nome",defaultValue = "")String nome){
        List <Perfil> list = service.download(nome.trim());
        return ResponseEntity.ok().body(list);

    }
    @PostMapping
    public ResponseEntity<Perfil> upload(@ModelAttribute PerfilDTO entity) {
        //TODO: process POST request
        Perfil perfil = service.upload(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(perfil.getId()).toUri();
		return ResponseEntity.created(uri).body(perfil);
    }
    
}
