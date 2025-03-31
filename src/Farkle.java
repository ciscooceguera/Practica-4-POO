import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// clase Farkle se encarga del flujo del juego
public class Farkle {
    // atributos
    private ArrayList<Jugador> jugadores;
    private ArrayList<Integer> puntajes, puntajesPosibles,puntajesObtenidos;
    private ArrayList<Dado> dados;
    private int turno, puntajeSkip, puntajeWin, numJugadores, puntajeTurno;
    // constructor
    public Farkle(int numJugadores, int puntajeSkip, int puntajeWin){
        this.numJugadores = numJugadores;
        this.puntajeSkip = puntajeSkip;
        this.puntajeWin = puntajeWin;
        // inicializo el arraylist de dados con dados con valor 0
        dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                .limit(6) // cuantos dados quiero guardar en el arraylist
                .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
        // inicializo arraylist de puntajes, donde nCopies recibe 6: tamaño, y 0: valor de cada posicion
        puntajes = new ArrayList<>(Collections.nCopies(6,0));
        jugadores = Stream.generate(() -> new Jugador())
                .limit(numJugadores)
                .collect(Collectors.toCollection(ArrayList::new));
        turno = 0;
    }
    // metodo para controlar el flujo del juego
    public void comenzarJuego(){
        puntajeTurno = 0;
        tirarDados();
        // 0 = Quiere seguir tirando
        evaluarPosiblesDadosParaPuntuar();
        mostrarDadosPosiblesEnVentana();
        verificarFarkled();
        if (decidirSiSeguirTirando() == 0){

        }else if (decidirSiSeguirTirando() == 1){ // 1 = Quiere pasar su turno
            evaluarSiJugadorPuedeParar();
            cambioDeTurno(); // cambia el turno actual
        }
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
            dado.tirarDado();
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
    // metodo para preguntar si desea seguir tirando
    public int decidirSiSeguirTirando(){
        int yesNoDecidirSiParar = JOptionPane.showConfirmDialog(null
                ,"Quieres seguir tirando?","Decidir"
                ,JOptionPane.YES_NO_OPTION);
        return yesNoDecidirSiParar;
    }
    // escoger con que dados se desea puntuar
    public void escogerDados(){

    }
    // metodo para evaluar si el jugador en turno puede parar
    public boolean evaluarSiJugadorPuedeParar(){
        return false;
    }
    public void evaluarPosiblesDadosParaPuntuar(){
        ArrayList<Integer> valoresDados = new ArrayList<>();
        // convierto a stream el arraylist que contiene los dados
        dados.stream().forEach(dado ->{
            // switch del valor en cada dado
            switch (dado.getValor()){
                // cada case son los valores que puede tener un dado [1 6]
                case 1:
                    // el arraylist puntajes va a contener el numero de 1s,2s,3s ... 6s que se obtuvo al tirar los 6 dados
                    puntajes.set(0,puntajes.get(0)+1);
                    valoresDados.add(dado.getValor());
                    break;
                case 2:
                    puntajes.set(1,puntajes.get(1)+1);
                    valoresDados.add(dado.getValor());
                    break;
                case 3:
                    puntajes.set(2,puntajes.get(2)+1);
                    valoresDados.add(dado.getValor());
                    break;
                case 4:
                    puntajes.set(3,puntajes.get(3)+1);
                    valoresDados.add(dado.getValor());
                    break;
                case 5:
                    puntajes.set(4,puntajes.get(4)+1);
                    valoresDados.add(dado.getValor());
                    break;
                case 6:
                    puntajes.set(5,puntajes.get(5)+1);
                    valoresDados.add(dado.getValor());
                    break;
            }
        });
        // evaluo los posibles puntajes que puede tener el jugador
        ArrayList<Integer> valoresPosibles = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            switch (i){
                // cada case son las posiciones del arraylist puntajes
                case 0:
                    if (puntajes.get(i)>=1){
                        valoresPosibles.add(i+1);
                    }
                    break;
                case 1:
                    // si hay menos de 3 2s no se puede puntuar
                    if (puntajes.get(i)>=3){
                        valoresPosibles.add(i+1);
                    }
                    break;
                case 2:
                    if (puntajes.get(i)>=3){
                        valoresPosibles.add(i+1);
                    }
                    break;
                case 3:
                    if (puntajes.get(i)>=3){
                        valoresPosibles.add(i+1);
                    }
                    break;
                case 4:
                    if (puntajes.get(i)>=1){
                        valoresPosibles.add(i+1);
                    }
                    break;
                case 5:
                    if (puntajes.get(i)>=3){
                        valoresPosibles.add(i+1);
                    }
                    break;
            }
        }
        puntajesPosibles = (ArrayList<Integer>) valoresPosibles.clone();
        puntajesObtenidos = (ArrayList<Integer>) valoresDados.clone();
    }
    // muestro en una ventana los dados con los que puede puntuar
    public void mostrarDadosPosiblesEnVentana(){
        // utilizo clase StringBuilder
        StringBuilder dadosPosiblesStr = new StringBuilder();
        // utilizo for each, para el valor en cada dado concatenarlo al stringbuilder
        puntajesPosibles.forEach(puntaje -> {
            // utilizo append para concatenar en el stringbuilder
            dadosPosiblesStr.append(puntaje).append(" ");
        });
        // muestro en ventana los dados puntuables
        JOptionPane.showMessageDialog(null,
                "Puedes puntuar con los dados: \n"+dadosPosiblesStr,
                "Puntuaciones posibles",
                JOptionPane.INFORMATION_MESSAGE);
        int pasar = 0;
        while (pasar == 0) {
            // se inicializa objecto botones, con un espacio que corresponde a los 6 dados
            // +1 que es para la opcion de pasar o continuar
            Object[] botones = new Object[puntajesObtenidos.size()+1];
            for (int i = 0; i < puntajesObtenidos.size(); i++) {
                botones[i] = puntajesObtenidos.get(i);
                if (i == puntajesObtenidos.size()-1){
                    botones[i+1] = "Continuar";
                }
            }
            int dadoSeleccionado = JOptionPane.showOptionDialog(null,
                    "Selecciona dado para puntuar",
                    "Warning",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    botones,
                    botones[botones.length-1]);

            if (dadoSeleccionado == botones.length-1){
                guardarPuntaje();
                pasar = 1;
                System.out.println("Pasar");
            }else if (evaluarPuntuarConElDado(dadoSeleccionado)){
                eliminarDado(dadoSeleccionado);
            }else{
                JOptionPane.showMessageDialog(null,
                        "No se puede puntuar con el dado seleccionado",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    // guardar puntaje
    public void guardarPuntaje(){
        Jugador jugador = jugadores.get(turno);
        jugador.sumarPuntaje(puntajeTurno);
        jugadores.set(turno,jugador);
        puntajeTurno=0;
    }
    // eliminar del arraylist el dado seleccionado
    public void eliminarDado(int dadoSeleccionado){
        int valor = puntajesObtenidos.get(dadoSeleccionado);
        int[] contador = {0};
        switch (valor){
            // cada case son las posiciones del arraylist puntajes
            case 1:
                if (puntajes.get(0)>=3){
                    puntajesObtenidos.removeIf(n-> n.equals(puntajesObtenidos.get(dadoSeleccionado))
                            &&contador[0]++<3);
                    puntajeTurno+=1000;
                }else{
                    puntajesObtenidos.remove(dadoSeleccionado);
                    puntajeTurno+=100;
                }
                break;
            case 2:
                if (puntajes.get(1)>=3){
                    puntajesObtenidos.removeIf(n-> n.equals(puntajesObtenidos.get(dadoSeleccionado))
                            &&contador[0]++<3);
                    puntajeTurno+=200;
                }
                break;
            case 3:
                if (puntajes.get(2)>=3){
                    puntajesObtenidos.removeIf(n-> n.equals(puntajesObtenidos.get(dadoSeleccionado))
                            &&contador[0]++<3);
                    puntajeTurno+=300;
                }
                break;
            case 4:
                if (puntajes.get(3)>=3){
                    puntajesObtenidos.removeIf(n-> n.equals(puntajesObtenidos.get(dadoSeleccionado))
                            &&contador[0]++<3);
                    puntajeTurno+=400;
                }
                break;
            case 5:
                if (puntajes.get(4)>=3){
                    puntajesObtenidos.removeIf(n-> n.equals(puntajesObtenidos.get(dadoSeleccionado))
                            &&contador[0]++<3);
                    puntajeTurno+=500;
                }else{
                    puntajesObtenidos.remove(dadoSeleccionado);
                    puntajeTurno+=50;
                }
                break;
            case 6:
                if (puntajes.get(5)>=3){
                    puntajesObtenidos.removeIf(n-> n.equals(puntajesObtenidos.get(dadoSeleccionado))
                            &&contador[0]++<3);
                    puntajeTurno+=600;
                }
                break;
        }
    }
    // evaluar si se puede puntuar con el dado seleccionado
    public boolean evaluarPuntuarConElDado(int posicion){
        int valor = puntajesObtenidos.get(posicion);
        System.out.println(valor);
        switch (valor){
            // cada case son las posiciones del arraylist puntajes
            case 1:
                if (puntajes.get(0)>=1){
                    return true;
                }
                break;
            case 2:
                // si hay menos de 3 2s no se puede puntuar
                if (puntajes.get(1)>=3){
                    return true;
                }
                break;
            case 3:
                if (puntajes.get(2)>=3){
                    return true;
                }
                break;
            case 4:
                if (puntajes.get(3)>=3){
                    return true;
                }
                break;
            case 5:
                if (puntajes.get(4)>=1){
                    return true;
                }
                break;
            case 6:
                if (puntajes.get(5)>=3){
                    return true;
                }
                break;
        }
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
