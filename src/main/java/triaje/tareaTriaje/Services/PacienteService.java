package triaje.tareaTriaje.Services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import triaje.tareaTriaje.Domain.Paciente;
import triaje.tareaTriaje.repositories.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public List<Paciente> buscar(String dni, String grado, String estado) {
        if (dni != null) {
            return pacienteRepository.findByDni(dni);
        } else if (grado != null) {
            return pacienteRepository.findByGrado(grado);
        } else if (estado != null) {
            return pacienteRepository.findByEstado(estado);
        } else {
            return pacienteRepository.findAll();
        }
    }

    public boolean addPaciente(Paciente paciente) {
        if (!pacienteRepository.existsById(paciente.getDni())) {
            pacienteRepository.save(paciente);
            return true;
        }
        return false;
    }
}
