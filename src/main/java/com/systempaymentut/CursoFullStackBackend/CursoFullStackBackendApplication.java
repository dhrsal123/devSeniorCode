package com.systempaymentut.CursoFullStackBackend;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.systempaymentut.CursoFullStackBackend.entities.Estudiante;
import com.systempaymentut.CursoFullStackBackend.entities.Pago;
import com.systempaymentut.CursoFullStackBackend.enums.PagoStatus;
import com.systempaymentut.CursoFullStackBackend.enums.TypePago;
import com.systempaymentut.CursoFullStackBackend.repository.EstudianteRepository;
import com.systempaymentut.CursoFullStackBackend.repository.PagoRepository;

@SpringBootApplication
public class CursoFullStackBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoFullStackBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository) {
		return args -> {
			estudianteRepository.save(Estudiante.builder()
				.id(UUID.randomUUID().toString())
				.nombre("Melissa")
				.apellido("Gordillo")
				.codigo("1234")
				.programaId("ISI123")
				.build());

			estudianteRepository.save(Estudiante.builder()
				.id(UUID.randomUUID().toString())
				.nombre("Carlos")
				.apellido("Ramírez")
				.codigo("5678")
				.programaId("ISI124")
				.build());
			
			estudianteRepository.save(Estudiante.builder()
				.id(UUID.randomUUID().toString())
				.nombre("Andrea")
				.apellido("López")
				.codigo("9101")
				.programaId("ISI125")
				.build());
			
			estudianteRepository.save(Estudiante.builder()
				.id(UUID.randomUUID().toString())
				.nombre("Juan")
				.apellido("Martínez")
				.codigo("1121")
				.programaId("ISI126")
				.build());
			
			estudianteRepository.save(Estudiante.builder()
				.id(UUID.randomUUID().toString())
				.nombre("Sofía")
				.apellido("Gómez")
				.codigo("3141")
				.programaId("ISI127")
				.build());
			
			estudianteRepository.save(Estudiante.builder()
				.id(UUID.randomUUID().toString())
				.nombre("Luis")
				.apellido("Fernández")
				.codigo("5161")
				.programaId("ISI128")
				.build());

			TypePago tiposPago[] = TypePago.values();

			Random random = new Random();

			estudianteRepository.findAll().forEach(estudiante -> {
				for (int i = 0; i < 10; i++) {
					int index = random.nextInt(tiposPago.length);

					 // Construir un objeto pago con valores aleatorios
					Pago pago = Pago.builder()
						.cantidad(1000.0 + Math.random() * 20000) // Convertimos el cálculo a Double
						.tipoPago(tiposPago[index]) // Cambiado de .type a .tipoPago para coincidir con el campo en la clase Pago
						.statusPago(PagoStatus.CREADO) // Cambiado de .status a .statusPago para coincidir con el campo en la clase Pago
						.fecha(LocalDate.now())
						.estudiante(estudiante)
						.build();

					pagoRepository.save(pago);
				}
			});
		};
	}
}
