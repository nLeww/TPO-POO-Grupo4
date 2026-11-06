import lotes.ControladorLote;
import lotes.Lote;
import personas.Habitante;
import personas.Titular;

public class main {
    public static void main(String[] args) {

        ControladorLote contLote = new ControladorLote(); //Metodo que se llama al inciio para crear el controlador

        Titular t1 = new Titular("Jose","Martinez",22549021);

        Habitante h1 = new Habitante("Martin", "Martinez", 2374738);
        Habitante h2 = new Habitante("Luisa", "Martinez", 11758290);

        Lote l1 = new Lote(101,10.0,t1);

        l1.agregarHabitante(h1);
        l1.agregarHabitante(h2);

        Lote l2 = new Lote(102,12,t1);

        contLote.agregarLote(l1); //deberian llamarse junto a la creacion de lote
        contLote.agregarLote(l2);

        contLote.listarLotes();




    }
}