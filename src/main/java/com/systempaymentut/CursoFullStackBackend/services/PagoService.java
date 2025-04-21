package com.systempaymentut.CursoFullStackBackend.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentut.CursoFullStackBackend.entities.Estudiante;
import com.systempaymentut.CursoFullStackBackend.entities.Pago;
import com.systempaymentut.CursoFullStackBackend.enums.PagoStatus;
import com.systempaymentut.CursoFullStackBackend.enums.TypePago;
import com.systempaymentut.CursoFullStackBackend.repository.EstudianteRepository;
import com.systempaymentut.CursoFullStackBackend.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional // Asegurar que metodos de la clase se ejecuten dentro de una transacción
public class PagoService {

    // Inyección de dependencias de la clase PagoRepository para interactuar con la
    // bd de pagos
    @Autowired
    private PagoRepository pagoRepository;

    // Inyección de dependencias de la clase EstudianteRepository para obtener
    // información de estudiantes desde la bd
    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * metodo para guardar el pago en la bd y almacenar un archivo pdf en el servidor 
     * 
     * @param file archivo pdf que se subira al servidor
     * @param cantidad monto del pago realizado
     * @param type tipo de pago EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO;
     * @param date fecha en la que se realizo el pago
     * @param codigoEstudiante codigo del estudiante que realizo el pago
     * @return Objeto Pago guardado en la bd
     * @throws IOException excepcion lanzada si ocurre un error al manejar el archivo PDF
     * 
     */

    public Pago savePago(MultipartFile file, double cantidad, TypePago typePago, LocalDate date, String codigoEstudiante) 
            throws IOException {

            /** 
             * Construir la ruta donde se guardara el archivo dentro del sistema
             * System.getProperty("user.home") obtiene la ruta del directorio personal del usuario del actual sistema operativo
             * Paths.get : Permite construir una ruta dentro del directorio personal en la carpeta "enset-data/pagos"
             * 
             */

            Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos");

            //Verifica si la carpeta existe, si no existe la crea
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            //Genera un nombre unico para el archivo usando UUID
            String fileName = UUID.randomUUID().toString(); 

            //Construye la ruta completa del archivo PDF
            Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos", fileName + ".pdf");

            //Guarda el archivo PDF en la ruta especificada dentro del sistema de archivos
            Files.copy(file.getInputStream(), filePath);

            //Buscamos el estudiante que hizo el pago usando su codigo
            Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

            //Creamos un nuevo objeto Pago usando el patron de diseño Builder
            Pago pago = Pago.builder()
                    .tipoPago(typePago) // Cambiado de .type(typePago) a .tipoPago(typePago)
                    .statusPago(PagoStatus.CREADO)
                    .fecha(date)
                    .estudiante(estudiante) 
                    .cantidad(cantidad)
                    .file(filePath.toUri().toString()) // Convertimos la ruta del archivo a una cadena de texto
                    .build(); // Construcción final del objeto Pago

            return pagoRepository.save(pago); 
                

        }

        public byte[] getArchivoPorId(Long pagoId) throws IOException {

            Pago pago = pagoRepository.findById(pagoId).get();
            /** 
             * Pago.getFile: Obtiene la URI del archivo guardado como una cadena de texto
             * URI.create: Convierte la cadena de texto en un objeto URI
             * Path.of: Convierte la URI en un path para poder acceder al archivo
             * Files.readAllBytes: Lee el contenido del archivo y lo convierte en un arreglo de bytes
             * Esto va a permitir que el archivo se pueda descargar desde el servidor
             */


            return Files.readAllBytes(Path.of(URI.create(pago.getFile())));

        }

        public Pago actualizarPagoPorStatus(PagoStatus status, Long id){

            //Busca el objeto Pago en la base de datos usando su id
            Pago pago = pagoRepository.findById(id).get();
            
            //Actualiza el estado del pago a "PAGADO" o "RECHAZADO"
            pago.setStatusPago(status);

            //Guarda el objeto Pago actualizado en la base de datos y lo devuelve
            return pagoRepository.save(pago);
    }

}
