package alura_challenge_back_end.api.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alura_challenge_back_end.api.entities.Depoimentos;

public interface DepoimentosRepository extends JpaRepository<Depoimentos,Long>{
    @Query("SELECT d FROM Depoimentos d")
    public Page<Depoimentos> find(Pageable pageRequest);
    @Query("SELECT d FROM Depoimentos d ORDER BY RANDOM()")
    public List<Depoimentos> findAleatorio(Pageable pageable);
}
