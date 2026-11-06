package Dispositivos;

public class Luminaria extends Dispositivo{

    public String Tipo_lampara;

    public String Potencia;

    public Luminaria(int codigo, String sector) {
        super(codigo, sector);
    }


    public Luminaria(int codigo, String sector, String tipo_lampara, String potencia) {
        super(codigo, sector);
        Tipo_lampara = tipo_lampara;
        Potencia = potencia;
    }

    public String getTipo_lampara() {
        return Tipo_lampara;
    }

    public void setTipo_lampara(String tipo_lampara) {
        Tipo_lampara = tipo_lampara;
    }

    public String getPotencia() {
        return Potencia;
    }

    public void setPotencia(String potencia) {
        Potencia = potencia;
    }
}
