package lotes;

import java.util.ArrayList;
import java.util.List;

public class ControladorLote {
    private List<Lote> lotes;

    public ControladorLote(){
        lotes = new ArrayList<>();
    }

    public void agregarLote(Lote l){
        lotes.add(l);
    }

    public void listarLotes(){
        for (Lote l : lotes){
            System.out.println(l);
        }
    }

    public Lote buscarLote(int numeroLote){
        for (Lote lote : lotes){
            if (lote.getNumero() == numeroLote)
                return lote;
        }
        throw new RuntimeException("Lote no encontrado");
    }
}
