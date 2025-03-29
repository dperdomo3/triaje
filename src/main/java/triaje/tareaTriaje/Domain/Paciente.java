package triaje.tareaTriaje.Domain;

public class Paciente {
    private String dni;
    private String nombre;
    private String apellido;
    private String genero;
    private String grado; //leve, grave, critico
    private String estado; //espera, consulta, alta

    public Paciente() {}
    
    public Paciente(String dni, String nombre, String apellido, String genero, String grado, String estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.grado = grado;
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getGrado() {
        return grado;
    }
    public void setGrado(String grado) {
        this.grado = grado;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
