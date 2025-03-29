package triaje.tareaTriaje.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import triaje.tareaTriaje.Domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, String>{
    List<Paciente> findByDni(String dni);
    List<Paciente> findByGrado(String grado);
    List<Paciente> findByEstado(String estado);
    List<Paciente> findByDniAndGradoAndEstado(String dni, String grado, String estado);
}
