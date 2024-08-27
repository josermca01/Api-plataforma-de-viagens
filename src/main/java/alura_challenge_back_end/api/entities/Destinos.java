package alura_challenge_back_end.api.entities;

import java.io.Serializable;

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

    private String foto1;
    private String foto2;

    @NotNull(message = "Destino precisa ter um preço")
    private double preco;

    public Destinos(Destinos insert){
        setNome(insert.getNome());
        setFoto1(insert.getFoto1());
        setFoto2(insert.getFoto2());
        setPreco(insert.getPreco());
    }
}
