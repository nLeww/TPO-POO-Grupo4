package excepciones;

// Se lanza cuando se viola una regla de negocio del barrio. */
public class ReglaNegocioException extends Exception {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}