package alura_challenge_back_end.api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Destinos")
@Table(name = "destinos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Destinos implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Destino precisa ser nomeado")
    private String nome;

    private List<String> foto = new ArrayList<String>();

    @NotBlank(message = "Destino precisa ter uma meta")
    private String meta;

    private String textoDescritivo;

    @NotNull(message = "Destino precisa ter um preço")
    private Double preco;

    public Destinos(String nome,String meta,String texto,Double preco){
        setNome(nome);
        setMeta(meta);
        setTextoDescritivo(texto);
        setPreco(preco);
    }
    public Destinos(Destinos insert){
        setNome(insert.getNome());
        setMeta(insert.getMeta());
        setFoto(insert.getFoto());
        setTextoDescritivo(insert.getTextoDescritivo());
        setPreco(insert.getPreco());
    }
    public void insertFotos(List<String> fotos){
        this.foto.addAll(fotos);
    }
}
