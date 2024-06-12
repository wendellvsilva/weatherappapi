package api.openweather.weatherapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.openweather.weatherapp.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Page<Cidade> findByCidade(String nome, Pageable pageable);
}
