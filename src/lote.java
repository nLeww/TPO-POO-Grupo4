public class lote {
    int tamanio;
    duenio_del_lote duenio;
    int ID_lote;
    habitantes[] habitantes;


    public lote(int tamanio, duenio_del_lote duenio, int ID_lote, habitantes[] habitantes) {
        this.tamanio = tamanio;
        this.duenio = duenio;
        this.ID_lote = ID_lote;
        this.habitantes = habitantes;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public duenio_del_lote getDuenio() {
        return duenio;
    }

    public void setDuenio(duenio_del_lote duenio) {
        this.duenio = duenio;
    }

    public int getID_lote() {
        return ID_lote;
    }

    public void setID_lote(int ID_lote) {
        this.ID_lote = ID_lote;
    }

    public habitantes[] getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(habitantes[] habitantes) {
        this.habitantes = habitantes;
    }
}
