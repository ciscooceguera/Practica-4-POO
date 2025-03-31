import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // inicializo el arraylist de dados con dados con valor 0
        dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                .limit(6) // cuantos dados quiero guardar en el arraylist
                .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
        turno = 0;
    }
    // metodo para controlar el flujo del juego
    public void comenzarJuego(){
        tirarDados();
    }
    // metodo para cambiar el turno
    public void cambioDeTurno(){
        // incremento el turno
        turno++;
        // si el turno es igual al numero de jugadores quiere decir que ya fue turno de todos, y vuelve a ser turno
        // del jugador 1
        if (turno == numJugadores){
            turno = 0;
        }
    }
    // metodo para tirarLos6Dados
    public void tirarDados(){
        // tiro los 6 dados y lo guardo en el arraylist
        for (int i = 0; i < 6; i++){
            Dado dado = new Dado();
            dado.tirar();
            dados.set(i,dado);
        }
        // utilizo clase StringBuilder
        StringBuilder resultadoDadosStr = new StringBuilder();
        // utilizo for each, para el valor en cada dado concatenarlo al stringbuilder
        dados.forEach(dado -> {
            // utilizo append para concatenar en el stringbuilder
            resultadoDadosStr.append(dado.getValor()).append(" ");
        });
        // Utilizo JOptionPane para mostrar en una ventana los valores obtenidos
        JOptionPane.showMessageDialog(null
                ,"Obtuviste los valores:\n"+resultadoDadosStr
                ,"Valores Obtenidos"
                ,JOptionPane.INFORMATION_MESSAGE);
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