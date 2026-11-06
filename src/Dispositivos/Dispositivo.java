package Dispositivos;

public class Dispositivo {
    public int Codigo;
    String sector;


    public Dispositivo(int codigo, String sector) {
        Codigo = codigo;
        this.sector = sector;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
