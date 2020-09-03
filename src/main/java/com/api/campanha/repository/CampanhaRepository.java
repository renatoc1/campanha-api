package com.api.campanha.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.campanha.model.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long>{

	@Query("select c from Campanha c where (:dtInicioVigencia between c.dtInicioVigencia and c.dtFimVigencia) "
			+ "or (:dtFimVigencia between c.dtInicioVigencia and c.dtFimVigencia) order by c.idTimeCoracao desc")
	List<Campanha> buscarPorVigencia(@Param("dtInicioVigencia") LocalDate dtInicioVigencia,@Param("dtFimVigencia") LocalDate dtFimVigencia);

	List<Campanha> findByIdTimeCoracao(Long id);

}
