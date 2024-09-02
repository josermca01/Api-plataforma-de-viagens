package alura_challenge_back_end.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import alura_challenge_back_end.api.entities.Destinos;
import alura_challenge_back_end.api.entities.DestinosDTO;
import alura_challenge_back_end.api.service.DestinosService;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DestinosController {
    public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/images";

    @Autowired
    private DestinosService service;

    @GetMapping("/destinos")
    public ResponseEntity<List<Destinos>> getDestinos(@RequestParam(value = "nome",defaultValue = "")String nome){
        List <Destinos> list = service.getDestino(nome.trim());
        return ResponseEntity.ok().body(list);

    }
    @GetMapping("/destinos/{id}")
	public ResponseEntity<DestinosDTO> getDestinosById(@PathVariable Long id){
		DestinosDTO dto = service.getDestinosById(id); 
		return ResponseEntity.ok().body(dto);
	}
    @PostMapping("/destinos")
    public ResponseEntity<Destinos> insert(@ModelAttribute Destinos entity,@RequestParam("image") MultipartFile file) throws IOException{
        if (entity.getNome()==null||entity.getPreco()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<String> ar = new ArrayList<String>();
        String originalFileName = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFileName);
        Files.write(fileNameAndPath,file.getBytes());
        ar.add(originalFileName);
        entity.setFoto(ar);
        Destinos dto = service.insert(entity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
    }
    
    @DeleteMapping("/destinos/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

    @PutMapping("/destinos/{id}")
	public ResponseEntity<Destinos> update(@RequestBody Destinos update, @PathVariable Long id){
		Destinos dto = service.update(id, update); 
		return ResponseEntity.ok().body(dto);
	}

}
