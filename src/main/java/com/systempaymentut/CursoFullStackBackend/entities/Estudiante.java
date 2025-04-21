package com.systempaymentut.CursoFullStackBackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id; // Import correcto
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad de la base de datos
@Builder // Builder es un patrón de diseño que permite crear objetos de forma más sencilla
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    private String id; // Identificador único para la entidad
    
    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String codigo;

    private String programaId;

    private String foto;
}
