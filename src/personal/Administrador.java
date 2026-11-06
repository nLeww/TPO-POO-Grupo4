package personal;

import java.util.ArrayList;
import java.util.List;

public class Administrador {

    private String nombre;
    private List<Personal> empleados;

    public void agregarEmpleado(Personal e) {
        if (e == null)
            throw new IllegalArgumentException ("El empleado no puede ser nulo");

        for (Personal empleado : empleados){
            if (empleado.getLegajo() == e.getLegajo())
                throw new IllegalArgumentException("Ese empleado ya esta registrado");
        }
            empleados.add(e);
        }

    public Personal buscarEmpleado(int legajo) {
        for (Personal empleado : empleados) {
            if (empleado.getLegajo() == legajo) {
                return empleado;
            }
        }
        return null;
    }

    public void mostrarEmpleados() {
        for (Personal empleado : empleados) {
            System.out.println(empleado);
        }
    }

    //Constructor
    public Administrador(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();
    }

}
