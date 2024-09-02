package alura_challenge_back_end.api.entities;

import org.springframework.web.multipart.MultipartFile;

public record PerfilDTO( String nome,
     String email,
     String senha,
     MultipartFile foto) {

}
