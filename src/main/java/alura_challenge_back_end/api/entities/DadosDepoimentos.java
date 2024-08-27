package alura_challenge_back_end.api.entities;

import jakarta.validation.constraints.NotBlank;

public record DadosDepoimentos(@NotBlank String nome,@NotBlank String depoimento) {

}
