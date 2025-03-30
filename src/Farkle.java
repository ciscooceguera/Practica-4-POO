import java.util.ArrayList;
// clase Farkle se encarga del flujo del juego
public class Farkle {
    // atributos
    private ArrayList<Dado> dados;
    private int turno, puntajeSkip, puntajeWin, numJugadores;
    // constructor
    public Farkle(int numJugadores, int puntajeSkip, int puntajeWin){
        this.numJugadores = numJugadores;
        this.puntajeSkip = puntajeSkip;
        this.puntajeWin = puntajeWin;
        dados = new ArrayList<>();
    }
    // metodo para iniciar el juego
    public void comenzarJuego(){

    }
    // metodo para cambiar el turno
    public void cambioDeTurno(){

    }
}
