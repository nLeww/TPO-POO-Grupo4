package Orden_y_Expensa;

import excepciones.ReglaNegocioException;
import personas.Titular;

import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Expensa {
    public int periodo;
    public float importe;
    private double capitalPagado;
    private double interesesPagados;
    public LocalDate fecha_vto;
    public int int_mora;
    private double interesesGenerados;
    private LocalDate fechaUltimoCalculoInteres;




    public Expensa(int periodo, float importe) {
        this.periodo = periodo;
        this.importe = importe;
        int anio = periodo / 100;
        int mes = periodo % 100;

        this.fecha_vto = LocalDate.of(anio, mes, 10);
        this.capitalPagado = 0;
        this.interesesPagados = 0;
        this.interesesGenerados = 0;
        this.fechaUltimoCalculoInteres = fecha_vto;
    }

    public int getPeriodo() {
        return periodo;
    }

    public float getImporte() {
        return importe;
    }

    public int getInt_mora() {
        return int_mora;
    }

    public double calcularInteresesPorMora() {
        LocalDate fechaActual = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(fechaUltimoCalculoInteres, fechaActual); //se fija la ultima vez que se calculo el interes, asi no se pone interes
        long meses = dias / 30;                                                      //donde ya se habia calculado antes
        if (meses > 0) {
            interesesGenerados += obtenerSaldoPendiente() * 0.03 * meses;
            fechaUltimoCalculoInteres =
                    fechaUltimoCalculoInteres.plusDays(meses * 30);
        }
        return interesesGenerados;
    }

    public boolean estaVencida() {
        return LocalDate.now().isAfter(fecha_vto);
    }

    public void RegistrarExpensa(int periodo, float importe, LocalDate fecha_vto, int int_mora, float totalExp, Titular titular){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Registro_de_Expensas.txt", true))) {

            bw.write(titular + "/" + periodo + "/" + importe + "/" + fecha_vto + "/" + int_mora + "/" + totalExp);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public double registrarPago(double monto) throws ReglaNegocioException {
        double intereses = obtenerInteresesPendientes();
        if (monto <= intereses) { //si el monto no cubre todos los intereses
            interesesPagados += monto;
            return 0;
        }
        interesesPagados += intereses;
        monto = monto - intereses; //el monto cubre el interes
        double saldo = obtenerSaldoPendiente();
        if (monto <= saldo) { //si el monto cubre tdo el saldo pendiente
            capitalPagado += monto;
            return 0;
        }
        capitalPagado += saldo;
        return monto - saldo;

    }

    public void LeerReclamos(){
        try(BufferedReader br = new BufferedReader(new FileReader("Registro_de_Expensas.txt"))){

            String linea;

            while ((linea = br.readLine()) != null){
                System.out.println(linea);
            }

        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public double obtenerSaldoPendiente() {
        return importe - capitalPagado;
    }


    public double obtenerInteresesPendientes() {
        double intereses = calcularInteresesPorMora();
        double pendientes = intereses - interesesPagados;
        return Math.max(0, pendientes); //valor que falta pagar, math.max mantiene el valor positivo
    }



    @Override
    public String toString() {
        return periodo + " " + importe + " " + fecha_vto;
    }
}
