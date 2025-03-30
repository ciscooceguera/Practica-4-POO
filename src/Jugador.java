// clase jugador se encargara de gestionar los puntajes de cada player
public class Jugador {
    private int puntaje;
    public Jugador() {
        puntaje = 0;
    }
    // metodo para recibir el puntaje del jugador
    public int obtenerPuntaje() {
        return puntaje;
    }
    // metodo para acumular el puntaje
    public void sumarPuntaje(int puntos) {
        puntaje += puntos;
    }
}
