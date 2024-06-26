package com.ynova.gestionCursos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ynova.gestionCursos.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findByTituloContainingIgnoreCase(String keyword, Pageable pageable);

}
