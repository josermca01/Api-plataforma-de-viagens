package alura_challenge_back_end.api.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alura_challenge_back_end.api.entities.Perfil;


public interface PerfilRepository extends JpaRepository<Perfil,Long> {
    @Query("SELECT d FROM Perfil d WHERE LOWER(d.nome)     LIKE   LOWER(CONCAT('%', :nome, '%'))")
    public List<Perfil> findByNome(String nome);
}
