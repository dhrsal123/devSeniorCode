package com.systempaymentut.CursoFullStackBackend.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType; // Asegúrate de que este import esté presente
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentut.CursoFullStackBackend.entities.Estudiante;
import com.systempaymentut.CursoFullStackBackend.entities.Pago;
import com.systempaymentut.CursoFullStackBackend.enums.PagoStatus;
import com.systempaymentut.CursoFullStackBackend.enums.TypePago;
import com.systempaymentut.CursoFullStackBackend.repository.EstudianteRepository;
import com.systempaymentut.CursoFullStackBackend.repository.PagoRepository;
import com.systempaymentut.CursoFullStackBackend.services.PagoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


//Define la clase como un controlador RES
@RestController
@CrossOrigin(origins = "*") // Permite el acceso a la API desde cualquier dominio

public class PagoController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    // Metodos para el manejo de estudiantes

    // Metodo para lista de todos los estudiantes
    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    // Metodo para un estudiante en especifico por su código
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantePorCodigo(@PathVariable String codigo) {
        return estudianteRepository.findByCodigo(codigo);
    }

    // Metodo que lista estudiantes según el programa academico
    @GetMapping("/estudiantesPorPorgrama")
    public List<Estudiante> listarEstudiantesPorPorgrama(@RequestParam String programaId) {
        return estudianteRepository.findByProgramaId(programaId);
    }

    //Metodo para el manejo de pagos

    //Metodo que lista los pagos registrados
    @GetMapping("/pagos")
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    //Metodo para pago ene specifico por su ID
    @GetMapping("/pagos/{id}")
    public Pago listarPagoPorId(@PathVariable Long id) {
        return pagoRepository.findById(id).get();
    }

    //Metodo que lista los pagos hechos por un estudiante en especifico según su código
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo) {
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    //Metodo que liste los pagos según su estado(CREADO, VALIDADO, RECHAZADO)
    @GetMapping("/pagosPorStatus")
    public List<Pago> listarPagosPorStatus(@RequestParam PagoStatus status) {
        return pagoRepository.findByStatusPago(status); // Cambiado de findByStatus a findByStatusPago
    }

    //Metodo que liste los pagos según su tipo(EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO)
    @GetMapping("/pagos/PorTipo")
    public List<Pago> listarPagosPorType(@RequestParam TypePago type) {
        return pagoRepository.findByTipoPago(type);
    }

    //Metodo para actualizar el estado de pago
    @PutMapping("/pagos/{id}/actualizarPago/")
    public Pago actualizarStatusDePago(@RequestParam PagoStatus status, @PathVariable Long pagoId) {
        return pagoService.actualizarPagoPorStatus(status, pagoId);
    }

    //Metodo para registrar un pago con archivo adjunto pdf como comprobante
    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(
        @RequestParam("file") MultipartFile file, 
        double cantidad,
        TypePago type,
        LocalDate date,
        String codigoEstudiante) 
        throws IOException {
            return pagoService.savePago(file, cantidad, type, date, codigoEstudiante);
    }
    
    //Metodo para descargar el archivo pdf del pago registrado
    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listarArchivoPorId(@PathVariable Long pagoId) throws IOException {
        return pagoService.getArchivoPorId(pagoId);
    }
    
    
    
}
