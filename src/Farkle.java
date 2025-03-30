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
        turno = 0;
    }
    // metodo para iniciar el juego
    public void comenzarJuego(){

    }
    // metodo para cambiar el turno
    public void cambioDeTurno(){

    }
    // metodo para tirarLos6Dados
    public void tirarDados(){

    }
    // metodo para preguntar si desea parar
    public void decidirSiParar(){

    }
    // metodo para evaluar si el jugador en turno puede parar
    public boolean evaluarSiJugadorPuedeParar(){
        return false;
    }
    // metodo para verificar si el jugador hizo un farkled, es decir; no puntuó de ninguna manera,
    // por lo que el puntaje que haya acumulado en su turno se pierde
    public boolean verificarFarkled(){
        return false;
    }
    // metodo para verificar si el jugador obtuvo dados calientes, es decir que el jugador haya logrado,
    // puntuar con los 6 dados en un turno
    public boolean verificarHotDice(){
        return false;
    }
}