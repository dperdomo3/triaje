package triaje.tareaTriaje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import triaje.tareaTriaje.domain.Paciente;
import triaje.tareaTriaje.services.PacienteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RestController
@RequestMapping("/api/v1")
public class PacienteController {
  @Autowired
  private PacienteService pacienteService;

  @Autowired
  private RabbitTemplate rabbit;

  @Value("${exchange}")
  private String exchange;

  @Value("${routingKeyUser}")
  private String routingKeyUser;

  @GetMapping("pacientes")
  public ResponseEntity<?> buscarPorFiltros(
      @RequestParam(required = false) String dni,
      @RequestParam(required = false) String grado,
      @RequestParam(required = false) String estado) {

    List<Paciente> resultado = pacienteService.buscar(dni, grado, estado);
    return new ResponseEntity<>(resultado, HttpStatus.OK);
  }

  @PostMapping("pacientes")
  public ResponseEntity<String> addPaciente(@RequestBody Paciente paciente) {
    boolean creado = pacienteService.addPaciente(paciente);
    if (creado) {
      try {
        String routingKey = "pacientes." + paciente.getGrado().toLowerCase();
        this.rabbit.convertAndSend(this.exchange, routingKey, paciente);
        return new ResponseEntity<>("Paciente creado y mensaje enviado a RabbitMQ", HttpStatus.CREATED);
      } catch (Exception e) {
        System.err.println("Error enviando el mensaje a RabbitMQ: " + e.getMessage());
        return new ResponseEntity<>("Paciente creado pero error al enviar el mensaje a RabbitMQ", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    return new ResponseEntity<>("No se pudo crear el paciente", HttpStatus.BAD_REQUEST);
  }
}
