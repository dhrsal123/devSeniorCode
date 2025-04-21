package com.systempaymentut.CursoFullStackBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempaymentut.CursoFullStackBackend.entities.Estudiante;


@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {
    
    // Este método busca un estudiante por su código
    Estudiante findByCodigo(String codigo);

    //Lista de estudiantes por programa
    List<Estudiante> findByProgramaId(String programaId);

    
}
