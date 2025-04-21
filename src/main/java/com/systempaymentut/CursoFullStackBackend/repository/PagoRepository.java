package com.systempaymentut.CursoFullStackBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempaymentut.CursoFullStackBackend.entities.Pago;
import com.systempaymentut.CursoFullStackBackend.enums.PagoStatus;
import com.systempaymentut.CursoFullStackBackend.enums.TypePago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    //lista de pagos asosciados a un estudiante
    List<Pago> findByEstudianteCodigo(String codigo);

    //lista de pagos por su estado
    List<Pago> findByStatusPago(PagoStatus statusPago); // Cambiado de findByStatus a findByStatusPago

    //Lista de pagos por tipo de pago
    List<Pago> findByTipoPago(TypePago tipoPago);
}
