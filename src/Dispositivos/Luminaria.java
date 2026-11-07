package Dispositivos;

import java.time.LocalDate;
import java.util.List;

public class Luminaria extends Dispositivo{

    public String Tipo_lampara;

    public String Potencia;

    public Luminaria(int codigo, String sector, LocalDate fechaInstalacion) {
        super(codigo, sector, fechaInstalacion);
    }


    public Luminaria(int codigo, String sector, LocalDate fechaInstalacion, String tipo_lampara, String potencia) {
        super(codigo, sector, fechaInstalacion);
        Tipo_lampara = tipo_lampara;
        Potencia = potencia;
    }

    @Override
    public PrioridadOrden calcularPrioridadOrden(List<Dispositivo> dispositivos) {
        int cantidad = 0;
        //ver si fallan mas de 3 luminarias de un mismo sector
        for (Dispositivo d : dispositivos) {
            if (d instanceof Luminaria && d.getSector().equals(getSector())
                    && d.getEstado() != EstadoDispositivo.OPERATIVO) {
                cantidad++;
            }
        }

        if (cantidad > 3) {
            return PrioridadOrden.ALTA;
        }
        return PrioridadOrden.NORMAL;
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
