package alura_challenge_back_end.api.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import alura_challenge_back_end.api.entities.Depoimentos;
import alura_challenge_back_end.api.repository.DepoimentosRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DepoimentosService {

    @Autowired
    private DepoimentosRepository repository;

    public Page<Depoimentos> getDepoimento(PageRequest pageRequest) {

        Page<Depoimentos> list = repository.find(pageRequest);
        return list;

    }
    public List<Depoimentos> getDepoimentoAleatorio() {
        Pageable primeiroResultado = PageRequest.of(0, 3,Sort.unsorted());

        List<Depoimentos> list = repository.findAleatorio(primeiroResultado);
        return list;

    }
    public Depoimentos getDepoimentosById(Long id) {
            Optional<Depoimentos> op = repository.findById(id);
            Depoimentos depoimento = op.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Depoimento nao encontrado"));
            return depoimento;
    }

    public Depoimentos insert(Depoimentos insert) {
        Depoimentos insertDepoimentos = new Depoimentos(insert);
        insertDepoimentos = repository.save(insertDepoimentos);
        return insertDepoimentos;
    }

    public void delete(Long id){
        repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        repository.deleteById(id);
    }

    public Depoimentos update(Long id, Depoimentos update) {
        try {
            Depoimentos entity = repository.getReferenceById(id);
            entity.setNome(update.getNome());
            entity.setDepoimento(update.getDepoimento());
            entity = repository.save(entity);
            return entity;
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Depoimento nao encontrado");
        }
    }


}
