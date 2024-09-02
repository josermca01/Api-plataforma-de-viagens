package alura_challenge_back_end.api.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import alura_challenge_back_end.api.entities.Perfil;
import alura_challenge_back_end.api.entities.PerfilDTO;
import alura_challenge_back_end.api.repository.PerfilRepository;
import alura_challenge_back_end.api.util.ImageUtils;
import java.io.IOException;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository repository;

    public Perfil upload(PerfilDTO dto){
        try {
            Perfil perfil = new Perfil();
            perfil.setNome(dto.nome());
            perfil.setEmail(dto.email());
            perfil.setSenha(dto.senha());
            byte[] compressedImage = ImageUtils.compressImage(dto.foto());
            perfil.setFoto(compressedImage);
            return repository.save(perfil);
        } catch (IOException error) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Erro na criacao do perfil");
        }
    }
    
    public List<Perfil> download(String nome){
        List<Perfil> perfil = repository.findByNome(nome);
        // for (Perfil perfil2 : perfil) {
        //     perfil2.setFoto(ImageUtils.decompressImage(perfil2.getFoto()));
        // }
        return perfil;
    }

}
