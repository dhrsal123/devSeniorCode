package com.systempaymentut.CursoFullStackBackend.entities;

import java.time.LocalDate;

import com.systempaymentut.CursoFullStackBackend.enums.PagoStatus;
import com.systempaymentut.CursoFullStackBackend.enums.TypePago;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad de la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Genera automáticamente el patrón Builder
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private Double cantidad;
    private TypePago tipoPago;   
    private PagoStatus statusPago;

    private String file;

    // Relación en la base de datos
    @ManyToOne
    private Estudiante estudiante; // Un pago pertenece a un estudiante
}
