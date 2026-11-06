package Dispositivos;

public class CamaraSeguridad extends Dispositivo{

    public String Resolucion;

    public int DiasGrabados;

    public CamaraSeguridad(int codigo, String sector) {
        super(codigo, sector);
    }

    public CamaraSeguridad(int codigo, String sector, String resolucion, int diasGrabados) {
        super(codigo, sector);
        Resolucion = resolucion;
        DiasGrabados = diasGrabados;
    }


    public String getResolucion() {
        return Resolucion;
    }

    public void setResolucion(String resolucion) {
        Resolucion = resolucion;
    }

    public int getDiasGrabados() {
        return DiasGrabados;
    }

    public void setDiasGrabados(int diasGrabados) {
        DiasGrabados = diasGrabados;
    }
}
