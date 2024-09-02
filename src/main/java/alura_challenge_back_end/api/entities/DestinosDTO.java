package alura_challenge_back_end.api.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DestinosDTO {
    private String nome;
    private String meta;
    private String textoDescritivo;
    private List<String> foto = new ArrayList<String>();
}
