package com.ynova.gestionCursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ynova.gestionCursos.entity.Curso;

@Repository
public interface CursoRespository extends JpaRepository<Curso, Long> {

}
