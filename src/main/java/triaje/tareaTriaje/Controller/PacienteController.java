package triaje.tareaTriaje.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import triaje.tareaTriaje.Domain.Paciente;
import triaje.tareaTriaje.Services.PacienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private RabbitTemplate rabitt;

  @Value("${exchange}")
  private String exchange;

  @GetMapping("pacientes")
  public ResponseEntity<List<Paciente>> getPacientes() {
    List<Paciente> pacientes = pacienteService.findAll();
    return new ResponseEntity<>(pacientes, HttpStatus.OK);
  }

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
      String routingKey = "pacientes." + paciente.getGrado().toLowerCase();
      rabitt.convertAndSend(exchange, routingKey, paciente);
      return ResponseEntity.status(HttpStatus.CREATED).body("Paciente registrado");
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al registrar paciente");
    }
  }
}
