package Orden_y_Expensa;

import java.util.Date;

public class Expensa {
    public int periodo;
    public float importe;
    public Date fecha_vto;
    public int int_mora;


    public Expensa(int periodo, float importe, Date fecha_vto, int int_mora) {
        this.periodo = periodo;
        this.importe = 50000;
        this.fecha_vto = fecha_vto;
        this.int_mora = 10000;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public Date getFecha_vto() {
        return fecha_vto;
    }

    public void setFecha_vto(Date fecha_vto) {
        this.fecha_vto = fecha_vto;
    }

    public int getInt_mora() {
        return int_mora;
    }

    public void setInt_mora(int int_mora) {
        this.int_mora = int_mora;
    }


    public float Calcular_interes(){
        int dias = 4;
        float totalExp = this.importe + (this.int_mora * dias);
        return totalExp;
    }
}
