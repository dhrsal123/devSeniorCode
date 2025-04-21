package com.systempaymentut.CursoFullStackBackend.dtos;

import java.time.LocalDate;

import com.systempaymentut.CursoFullStackBackend.enums.TypePago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPago {
    
    private Double cantidad;
    private TypePago typePago;  
    private LocalDate date;

    private String codigoEstudiante;


}
