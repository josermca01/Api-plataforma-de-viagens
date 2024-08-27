package alura_challenge_back_end.api.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alura_challenge_back_end.api.entities.Destinos;

public interface DestinosRepository extends JpaRepository<Destinos,Long>{
    @Query("SELECT d FROM Destinos d")
    public Page<Destinos> find(Pageable pageRequest);

    @Query("SELECT d FROM Destinos d WHERE LOWER(d.nome)     LIKE   LOWER(CONCAT('%', :nome, '%'))")
    public List<Destinos> findByNome(String nome);

    @Query("SELECT d FROM Destinos d WHERE LOWER(d.foto1)     LIKE   LOWER(CONCAT('%', :foto1, '%'))")
    public List<Destinos> findByFotos(String foto1);
}
