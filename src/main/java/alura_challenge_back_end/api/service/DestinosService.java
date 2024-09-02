package alura_challenge_back_end.api.service;

import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import alura_challenge_back_end.api.entities.Destinos;
import alura_challenge_back_end.api.entities.DestinosDTO;
import alura_challenge_back_end.api.repository.DestinosRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DestinosService {
    @Autowired
    private DestinosRepository repository;

    

    public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/images";


    public List<Destinos> getDestino(String nome) {
        List<Destinos> list = repository.findByNome(nome);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum destino encontrado");
        }
        return list;
        }
    

    public DestinosDTO getDestinosById(Long id) {
            Optional<Destinos> op = repository.findById(id);
            Destinos destino = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Destino nao encontrado"));
            DestinosDTO dto = new DestinosDTO(destino.getNome(), destino.getMeta(),destino.getTextoDescritivo(), destino.getFoto());
            return dto;
    }


    public Destinos insert(Destinos entity) {
        Destinos insertDestinos = new Destinos(entity);
        if (insertDestinos.getMeta().length()>160) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Texto de meta deve ter no maximo 160 caracteres");
        }
        insertDestinos = repository.save(insertDestinos);
        return insertDestinos;
    }

    
    public void delete(Long id){
        Optional<Destinos> op = repository.findById(id);
        Destinos destino = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Destino nao encontrado"));
        repository.deleteById(id);
        verificaFotoRepitida(destino.getFoto());
    }

    private void verificaFotoRepitida(List<String> fotoDeletar){
        try{
        List<Destinos> destinos = repository.findAll();
            for (String fotoDeletar1 : fotoDeletar) {
                int count = 0;
                for (Destinos destinos2 : destinos) {
                    if (destinos2.getFoto().contains(fotoDeletar1)) {
                        count++;
                    }
                }
                if (count==0) {
                    Path fileNameAndPath = Paths.get(uploadDirectory, fotoDeletar1);
                    Files.delete(fileNameAndPath);
                } 
        }
        }
        catch(IOException error){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum destino encontrado");
        }
    }


    public Destinos update(Long id, Destinos update) {
        try {
            Destinos entity = repository.getReferenceById(id);
            entity.setNome(update.getNome());
            entity.setPreco(update.getPreco());
            entity = repository.save(entity);
            return entity;
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Destino nao encontrado");
        }
    }

}
