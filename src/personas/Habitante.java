package personas;

public class Habitante extends Persona {


    //Constructores, Getter y Setter
    public Habitante(String nombre, String apellido, int documento, int telefono, String correo_electronico) {
        super(nombre, apellido, documento, telefono, correo_electronico);
    }

    public Habitante(String nombre, String apellido, int documento) {
        super(nombre, apellido, documento);
    }
}
