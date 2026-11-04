package personas;

public class Titular extends Persona{



    //Constructores
    public Titular(String nombre, String apellido, int documento, int telefono, String correo_electronico) {
        super(nombre, apellido, documento, telefono, correo_electronico);
    }

    public Titular(String nombre, String apellido, int documento) {
        super(nombre, apellido, documento);
    }
}
