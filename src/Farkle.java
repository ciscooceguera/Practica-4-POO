import javax.swing.*;
import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// clase Farkle se encarga del flujo del juego
public class Farkle {
    // atributos
    private ArrayList<Jugador> jugadores;
    private ArrayList<Integer> puntajes, puntajesPosibles,puntajesObtenidos;
    private ArrayList<Dado> dados, dadosEliminados;
    private int turno, puntajeWin, numJugadores, puntajeTurno, numDados,numDadosActual, posicionGanador;
    // constructor
    public Farkle(int numJugadores, int puntajeWin){
        this.numJugadores = numJugadores;
        this.puntajeWin = puntajeWin;
        // inicializo el arraylist de dados con dados con valor 0
        dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                .limit(6) // cuantos dados quiero guardar en el arraylist
                .limit(6) // cuantos dados quiero guardar en el arraylist
                .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
        // inicializo arraylist de puntajes, donde nCopies recibe 6: tamaño, y 0: valor de cada posicion
        puntajes = new ArrayList<>(Collections.nCopies(6,0));
        jugadores = Stream.generate(() -> new Jugador())
                .limit(numJugadores)
                .collect(Collectors.toCollection(ArrayList::new));
        turno = 0;
        numDadosActual = 6;
        numDados = 6;
    }
    public void setDados(){

            if (dados.size()==1){
                Dado dado = new Dado();
                dado.setValor(1);
                dados.set(0,dado);
            }
            if (dados.size()==2){
                Dado dado = new Dado();
                dado.setValor(5);
                dados.set(0,dado);
                dado.setValor(3);
                dados.set(1,dado);
            }
            if (dados.size()==3){
                Dado dado = new Dado();
                dado.setValor(5);
                dados.set(0,dado);
                dado.setValor(1);
                dados.set(1,dado);
                dado.setValor(4);
                dados.set(2,dado);

            }
            if (dados.size()==4){
                Dado dado = new Dado();
                dado.setValor(5);
                dados.set(0,dado);
                dado.setValor(1);
                dados.set(1,dado);
                dado.setValor(1);
                dados.set(2,dado);
                dado.setValor(4);
                dados.set(3,dado);
            }
            if (dados.size()==5){
                Dado dado = new Dado();
                dado.setValor(5);
                dados.set(0,dado);
                dado.setValor(1);
                dados.set(1,dado);
                dado.setValor(1);
                dados.set(2,dado);
                dado.setValor(1);
                dados.set(3,dado);
                dado.setValor(4);
                dados.set(4,dado);
            }
            if (dados.size()==6){
                Dado dado = new Dado();
                dado.setValor(1);
                dados.set(0,dado);
                dado.setValor(2);
                dados.set(1,dado);
                dado.setValor(3);
                dados.set(2,dado);
                dado.setValor(4);
                dados.set(3,dado);
                dado.setValor(5);
                dados.set(4,dado);
                dado.setValor(6);
                dados.set(5,dado);
            }


        // utilizo clase StringBuilder
        StringBuilder resultadoDadosStr = new StringBuilder();
        // utilizo for each, para el valor en cada dado concatenarlo al stringbuilder
        dados.forEach(dado -> {
            // utilizo append para concatenar en el stringbuilder
            resultadoDadosStr.append(dado.getValor()).append(" ");
        });
        //Utilizo JOptionPane para mostrar en una ventana los valores obtenidos
        JOptionPane.showMessageDialog(null
                ,"Obtuviste los valores:\n"+resultadoDadosStr
                ,"Valores Obtenidos"
                ,JOptionPane.INFORMATION_MESSAGE);
        //Se toma la posicion (x, y) del primer dado para poder dibujar los siguientes
        AtomicInteger x= new AtomicInteger(dados.get(0).getXPosicion());
        int y=dados.get(0).getYPosicion();
        //Se itera para poder dibujar cada dado en el Canvas
//        for(int i=0; i<dados.size(); i++){
//            Dado dado = dados.get(i);
//            dado.mover(x,y);
//            dado.mostrarEnCanvas(dado.getValor());
//            x+=dado.getTamañoCara()+10;
//        }
        dados.stream().
                forEach(dado -> {
                    dado.mover(x.get(),y);
                    dado.mostrarEnCanvas(dado.getValor());
                    x.addAndGet(dado.getTamañoCara() + 10);
                });
    }
    // metodo para controlar el flujo del juego
    public void controlarFlujoDelJuego(){
        boolean hayGanador = false;
        int turnoActual = 1;
        int hotdice=0;
        while (!hayGanador){
            ocultarDados();
            hotdice=0;
            /*if (dados.size()==2){
                setDados();
            }else {
                tirarDados();
            }*/
            tirarDados();
            evaluarPosiblesDadosParaPuntuar();
            if (verificarFarkled()){
                puntajeTurno=0;
                turnoActual=2;
            }else if (verificarHotDice()){
                hotdice=1;
                sumarHotDice();
                System.out.println("Puntaje: "+puntajeTurno);
                puntajes.clear();
                puntajes = new ArrayList<>(Collections.nCopies(6,0));
                 //inicializo el arraylist de dados con dados con valor 0
                dados.clear();
                dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                       .limit(6) // cuantos dados quiero guardar en el arraylist
                        .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
                // inicializo arraylist de puntajes, donde nCopies recibe 6: tamaño, y 0: valor de cada posicion
                puntajesObtenidos.clear();
                puntajesPosibles.clear();
                numDadosActual = 6;
                numDados = 6;
                int seguirTirando = decidirSiSeguirTirando();
                if (seguirTirando == 0){
                    hayGanador = evaluarSiHayGanador();
                    turnoActual = 2;
                }

            }else{
                turnoActual = mostrarDadosPosiblesEnVentana();
            }
            //
            if (turnoActual == 2){
                guardarPuntaje();
                ocultarDados();
                cambioDeTurno();
                puntajes.clear();
                puntajes = new ArrayList<>(Collections.nCopies(6,0));
                // inicializo el arraylist de dados con dados con valor 0
                dados.clear();
                dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                        .limit(6) // cuantos dados quiero guardar en el arraylist
                        .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
                // inicializo arraylist de puntajes, donde nCopies recibe 6: tamaño, y 0: valor de cada posicion
                puntajesObtenidos.clear();
                puntajesPosibles.clear();
                hayGanador = evaluarSiHayGanador();
                numDadosActual = 6;
                numDados = 6;
            }else if (turnoActual == 1){
                ocultarDados();
                puntajesPosibles.clear();
                puntajesObtenidos.clear();
                puntajes.clear();
                puntajes = new ArrayList<>(Collections.nCopies(6,0));
            }
        }
        posicionGanador = encontrarGanador();
        mostrarGanador(posicionGanador);
    }
    // muestra ganador
    public void mostrarGanador(int posicion){
        Jugador jugador = jugadores.get(posicion);
        int numJugador = posicion+1;
        JOptionPane.showMessageDialog(null,
                "Ganador: Jugador "+numJugador+"\nPuntaje obtenido: "+jugador.obtenerPuntaje(),
                "Mensaje Victoria",
                JOptionPane.INFORMATION_MESSAGE);
    }
    // encontrar la posicion del jugador ganador
    public int encontrarGanador(){
        OptionalInt maxPuntaje = jugadores.stream()
                .mapToInt(Jugador::obtenerPuntaje)
                .max();
        // Si hay un puntaje máximo, busca la posición
        if (maxPuntaje.isPresent()) {
            int max = maxPuntaje.getAsInt();
            // Encuentra el índice del jugador con el puntaje máximo
            return IntStream.range(0, jugadores.size())
                    .filter(i -> jugadores.get(i).obtenerPuntaje() == max)
                    .findFirst()
                    .orElse(-1); // Devuelve -1 si no se encuentra el índice
        }
        return -1;
    }
    // evaluar si hay ganador
    public boolean evaluarSiHayGanador(){
        boolean hayGanador = jugadores.stream().anyMatch(jugador -> jugador.obtenerPuntaje()>=puntajeWin);
        return hayGanador;
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
        // Utilizo JOptionPane para mostrar en una ventana los valores obtenidos
        JOptionPane.showMessageDialog(null,
                "Turno actual del jugador " + (turno+1),
                "Notificacion",
                JOptionPane.INFORMATION_MESSAGE);
        // tiro los 6 dados y lo guardo en el arraylist
        for (int i = 0; i < dados.size(); i++){
            Dado dado = new Dado();
            dado.tirar();
            dados.set(i,dado);
        }

        ocultarDados();

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


        //Utilizo JOptionPane para mostrar en una ventana los valores obtenidos
        JOptionPane.showMessageDialog(null
                ,"Obtuviste los valores:\n"+resultadoDadosStr
               ,"Valores Obtenidos"
                ,JOptionPane.INFORMATION_MESSAGE);
        //Se toma la posicion (x, y) del primer dado para poder dibujar los siguientes
        AtomicInteger x= new AtomicInteger(dados.get(0).getXPosicion()); //Dato atomico que permite cambiar su valor dentro de una Lambda
        int y=dados.get(0).getYPosicion();

        //Se utiliza una lambda con forEach y Stream para poder dibujar todos los datos en pantalla
        dados.stream().
                forEach(dado -> {
                   dado.mover(x.get(),y);
                   dado.mostrarEnCanvas(dado.getValor());
                   x.addAndGet(dado.getTamañoCara() + 10); //
                });
    }
    // metodo para preguntar si desea seguir tirando
    public int decidirSiSeguirTirando(){
        int yesNoDecidirSiTirar = JOptionPane.showConfirmDialog(null
                ,"Quieres seguir tirando?","Decidir"
                ,JOptionPane.YES_NO_OPTION);
        if (yesNoDecidirSiTirar == 1){
            return 0;
        }
        return 1;
    }
    // escoger con que dados se desea puntuar
    public void escogerDados(){

    }
    // metodo para evaluar si el jugador en turno puede parar
    public boolean evaluarSiJugadorPuedeParar(){
        return false;
    }
    // guardo en arraylists los valores de los dados obtenidos
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
        for (int i = 0; i < dados.size(); i++){
            switch (i){
                // cada case son las posiciones del arraylist puntajes
                case 0:
                    if (puntajes.get(0)>=1){
                        valoresPosibles.add(1);
                    }
                    break;
                case 1:
                    // si hay menos de 3 2s no se puede puntuar
                    if (puntajes.get(1)>=3){
                        valoresPosibles.add(2);
                    }
                    break;
                case 2:
                    if (puntajes.get(2)>=3){
                        valoresPosibles.add(3);
                    }
                    break;
                case 3:
                    if (puntajes.get(3)>=3){
                        valoresPosibles.add(4);
                    }
                    break;
                case 4:
                    if (puntajes.get(4)>=1){
                        valoresPosibles.add(5);
                    }
                    break;
                case 5:
                    if (puntajes.get(5)>=3){
                        valoresPosibles.add(6);
                    }
                    break;
            }
        }
        puntajesPosibles = (ArrayList<Integer>) valoresPosibles.clone();
        puntajesObtenidos = (ArrayList<Integer>) valoresDados.clone();
    }
    // muestro en una ventana los dados con los que puede puntuar
    public int mostrarDadosPosiblesEnVentana(){

        int continuarPuntuando = 0;
        while (continuarPuntuando == 0) {
            // se inicializa objecto botones, con un espacio que corresponde a los 6 dados
            // +1 que es para la opcion de pasar o continuar
            Object[] botones = new Object[dados.size() + 2];
            for (int i = 0; i < dados.size(); i++) {
                botones[i] = dados.get(i).getValor();
                if (i == dados.size() - 1) {
                    botones[i + 1] = "Seguir tirando";
                    botones[i + 2] = "Bank";
                }
            }
            int dadoSeleccionado = JOptionPane.showOptionDialog(null,
                    "Selecciona dado para puntuar",
                    "Warning",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    botones,
                    botones[botones.length - 1]);
            if (dadoSeleccionado == botones.length - 1) {
                if (numDadosActual < numDados) {
                    JOptionPane.showMessageDialog(null,
                            "Puntaje guardado correctamente",
                            "Notificacion",
                            JOptionPane.INFORMATION_MESSAGE);
                    numDados = 6;
                    numDadosActual = 6;
                    return 2;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No puedes bankear sin haber seleccionado un dado previamente",
                            "Warning",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (dadoSeleccionado == botones.length - 2) {
                if (numDadosActual < numDados) {
                    JOptionPane.showMessageDialog(null,
                            "Tirando dados restantes",
                            "Notificacion",
                            JOptionPane.INFORMATION_MESSAGE);
                    numDados = numDadosActual;
                    return 1;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No puedes volver a tirar sin haber seleccionado un dado previamente",
                            "Warning",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (evaluarPuntuarConElDado(dadoSeleccionado)) {
                numDadosActual--;
                eliminarDado(dadoSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se puede puntuar con el dado seleccionado",
                        "Warning",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return 0;
    }
    // guardar puntaje
    public void guardarPuntaje(){
        Jugador jugador = jugadores.get(turno);
        jugador.sumarPuntaje(puntajeTurno);
        jugadores.set(turno,jugador);
        puntajeTurno=0;
    }

    //Metodo que incluye una lambda para ocultar todos los dados
    public void ocultarDados(){
        dados.stream().forEach(dado -> dado.ocultar());
    }

    //Metodo que oculta 1 solo dado con un valor en especifico mediante una lambda
    public void ocultarDadoValor(int valor){
        Optional<Dado> dadoBusqueda = dados.stream().
                filter(dado -> dado.getValor()==valor).
                findFirst();
        dadoBusqueda.get().ocultar();
    }

    //Metodo que oculta varios dados con un valor en especifico mediante una lambda
    public void ocultarDadosValor(int valor) {
        dados.stream()
                .filter(dado -> dado.getValor()==valor)
                .limit(3)
                .forEach(dado -> dado.ocultar());

    }

    // eliminar del arraylist el dado seleccionado y borra el dado, retorna el # de dados que borro
    public int eliminarDado(int dadoSeleccionado){
        int valor = puntajesObtenidos.get(dadoSeleccionado);
        int[] contador = {0};
        int[] contador2= {0};
        puntajesPosibles.removeIf(n-> n == valor);
        switch (valor){
            // cada case son las posiciones del arraylist puntajes
            case 1:
                if (puntajes.get(0)>=3){
                    ocultarDadosValor(valor);
                    puntajesObtenidos.removeIf(n-> n == valor
                            &&contador[0]++<3);
                    puntajes.set(0,puntajes.get(0)-3);
                    dados.removeIf(dado -> dado.getValor() == valor
                            &&contador2[0]++<3);
                    puntajeTurno+=1000;
                }else{
                    ocultarDadoValor(valor);
                    puntajesObtenidos.remove(dadoSeleccionado);
                    dados.remove(dadoSeleccionado);
                    puntajes.set(0,puntajes.get(0)-1);
                    puntajeTurno+=100;
                }
                break;
            case 2:
                if (puntajes.get(1)>=3){
                    ocultarDadosValor(valor);
                    puntajesObtenidos.removeIf(n-> n == valor
                            &&contador[0]++<3);
                    dados.removeIf(dado -> dado.getValor()==valor
                            &&contador2[0]++<3);
                    puntajes.set(1,puntajes.get(1)-3);
                    puntajeTurno+=200;
                }
                break;
            case 3:
                if (puntajes.get(2)>=3){
                    ocultarDadosValor(valor);
                    puntajesObtenidos.removeIf(n-> n == valor
                            &&contador[0]++<3);
                    dados.removeIf(dado -> dado.getValor()== valor
                            &&contador2[0]++<3);
                    puntajes.set(2,puntajes.get(2)-3);
                    puntajeTurno+=300;
                }
                break;
            case 4:
                if (puntajes.get(3)>=3){
                    ocultarDadosValor(valor);
                    puntajesObtenidos.removeIf(n-> n == valor
                            &&contador[0]++<3);
                    dados.removeIf(dado -> dado.getValor()==valor
                            &&contador2[0]++<3);
                    puntajes.set(3,puntajes.get(3)-3);
                    puntajeTurno+=400;
                }
                break;
            case 5:
                if (puntajes.get(4)>=3){
                    ocultarDadosValor(valor);
                    puntajesObtenidos.removeIf(n-> n == valor
                            &&contador[0]++<3);
                    dados.removeIf(dado -> dado.getValor() == valor
                            &&contador2[0]++<3);
                    puntajes.set(4,puntajes.get(4)-3);
                    puntajeTurno+=500;
                }else{
                    ocultarDadoValor(valor);
                    puntajesObtenidos.remove(dadoSeleccionado);
                    dados.remove(dadoSeleccionado);
                    puntajeTurno+=50;
                    puntajes.set(4,puntajes.get(4)-1);
                }
                break;
            case 6:
                if (puntajes.get(5)>=3){
                    ocultarDadosValor(valor);
                    puntajesObtenidos.removeIf(n-> n == valor
                            &&contador[0]++<3);
                    dados.removeIf(dado -> dado.getValor()==valor
                            &&contador2[0]++<3);
                    puntajes.set(5,puntajes.get(5)-3);
                    puntajeTurno+=600;
                }
                break;
        }
        if (contador2[0]>0){
            return 3;
        }else{
            return 1;
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
        for (int i = 0; i<puntajesObtenidos.size(); i++){
            int valor = puntajesObtenidos.get(i);
            switch (valor){
                // cada case son las posiciones del arraylist puntajes
                case 1:
                    if (puntajes.get(0)>=1){
                        return false;
                    }
                    break;
                case 2:
                    // si hay menos de 3 2s no se puede puntuar
                    if (puntajes.get(1)>=3){
                        return false;
                    }
                    break;
                case 3:
                    if (puntajes.get(2)>=3){
                        return false;
                    }
                    break;
                case 4:
                    if (puntajes.get(3)>=3){
                        return false;
                    }
                    break;
                case 5:
                    if (puntajes.get(4)>=1){
                        return false;
                    }
                    break;
                case 6:
                    if (puntajes.get(5)>=3){
                        return false;
                    }
                    break;
            }
        }
        // mensaje en ventana de Farkle
        JOptionPane.showMessageDialog(null,
                "Hiciste Farkle",
                "Mensaje Farkle",
                JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    // metodo para verificar si el jugador obtuvo dados calientes, es decir que el jugador haya logrado,
    // puntuar con los 6 dados en un turno
    public boolean verificarHotDice(){

        int[] contador = {0};
        int contadorJugadasPosibles = 0;
        ArrayList<Integer> copiaPuntajesObtenidos = new ArrayList<>(puntajesObtenidos);
        ArrayList<Integer> copiaPuntajes = new ArrayList<>(puntajes);
        System.out.println(copiaPuntajesObtenidos.size());
        if (dados.size()==2){
            System.out.println("Caso verificar hot dice quedan 2 dados");
            if (copiaPuntajesObtenidos.get(0)==1 || copiaPuntajesObtenidos.get(0)==5){
                if (copiaPuntajesObtenidos.get(1)==1 || copiaPuntajesObtenidos.get(1)==5) {
                    contadorJugadasPosibles += 2;
                }
            }
        }else if (dados.size()==1) {
            if (copiaPuntajesObtenidos.get(0)==1|| copiaPuntajesObtenidos.get(0)==5){
                contadorJugadasPosibles+=1;
            }
        }else {
            int contadorJugadasFallidas = 0;
            while (!copiaPuntajesObtenidos.isEmpty()&& contadorJugadasFallidas<copiaPuntajesObtenidos.size()){
                contador[0] = 0;
                int i = 0;
                System.out.println(i);
                int valor = copiaPuntajesObtenidos.get(i);
                switch (valor) {
                    // cada case son las posiciones del arraylist puntajes
                    case 1:
                        if (copiaPuntajes.get(0) >= 3) {
                            contadorJugadasPosibles += copiaPuntajes.get(0);
                            copiaPuntajesObtenidos.removeIf(n -> n == valor);
                            copiaPuntajes.set(0,copiaPuntajes.get(0)-3);
                        } else if (puntajes.get(0) >= 1) {
                            contadorJugadasPosibles += 1;
                            copiaPuntajesObtenidos.remove(i);
                            copiaPuntajes.set(0,copiaPuntajes.get(0)-1);
                        }else{
                            contadorJugadasFallidas+=1;
                        }
                        break;
                    case 2:
                        if (copiaPuntajes.get(1) >= 3) {
                            contadorJugadasPosibles += 3;
                            copiaPuntajesObtenidos.removeIf(n -> n == valor&&contador[0]++<3);
                            copiaPuntajes.set(1,copiaPuntajes.get(1)-3);
                        }else{
                            contadorJugadasFallidas+=1;
                        }
                        break;
                    case 3:
                        if (copiaPuntajes.get(2) >= 3) {
                            contadorJugadasPosibles += 3;
                            copiaPuntajesObtenidos.removeIf(n -> n == valor&&contador[0]++<3);
                            copiaPuntajes.set(2,copiaPuntajes.get(2)-3);
                        }else{
                            contadorJugadasFallidas+=1;
                        }
                        break;
                    case 4:
                        if (copiaPuntajes.get(3) >= 3) {
                            contadorJugadasPosibles += 3;
                            copiaPuntajesObtenidos.removeIf(n -> n == valor&&contador[0]++<3);
                            copiaPuntajes.set(3,copiaPuntajes.get(3)-3);
                        }else{
                            contadorJugadasFallidas+=1;
                        }
                        break;
                    case 5:
                        if (copiaPuntajes.get(4) >= 3) {
                            contadorJugadasPosibles += copiaPuntajes.get(4);
                            copiaPuntajesObtenidos.removeIf(n -> n == valor);
                            copiaPuntajes.set(4,copiaPuntajes.get(4)-3);
                        } else if (puntajes.get(4) >= 1) {
                            contadorJugadasPosibles += 1;
                            copiaPuntajes.set(4,copiaPuntajes.get(4)-1);
                            copiaPuntajesObtenidos.remove(i);
                        }else{
                            contadorJugadasFallidas+=1;
                        }
                        break;
                    case 6:
                        if (copiaPuntajes.get(5) >= 3) {
                            contadorJugadasPosibles += 3;
                            copiaPuntajesObtenidos.removeIf(n -> n == valor&&contador[0]++<3);
                            copiaPuntajes.set(5,copiaPuntajes.get(5)-3);
                        }else{
                            contadorJugadasFallidas+=1;
                        }
                        break;

                }
            }
        }

        if (contadorJugadasPosibles == dados.size()) {
            // mensaje en ventana de Hot Dice
            JOptionPane.showMessageDialog(null,
                    "Hiciste Hot Dice",
                    "Mensaje Hot Dice",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }else{
            return false;
        }


    }
    public void sumarHotDice(){
        int[] contador = {0};

        for (int i = 0; i<puntajesObtenidos.size(); i++){
            contador[0]=0;
            int valor = puntajesObtenidos.get(i);
            switch (valor){
                // cada case son las posiciones del arraylist puntajes
                case 1:
                    if (puntajes.get(0)>=3){
                        puntajesObtenidos.removeIf(n-> n.equals(valor)
                                &&contador[0]++<3);
                        puntajeTurno+=1000;
                    }else if(puntajes.get(0)>=1){
                        puntajesObtenidos.remove(i);
                        puntajeTurno+=100;
                    }
                    break;
                case 2:
                    // si hay menos de 3 2s no se puede puntuar
                    if (puntajes.get(1)>=3){
                        puntajesObtenidos.removeIf(n-> n.equals(valor)
                                &&contador[0]++<3);
                        puntajeTurno+=200;
                    }
                    break;
                case 3:
                    if (puntajes.get(2)>=3){
                        if (puntajes.get(1)>=3){
                            puntajesObtenidos.removeIf(n-> n.equals(valor)
                                    &&contador[0]++<3);
                            puntajeTurno+=300;
                        }
                    }
                    break;
                case 4:
                    if (puntajes.get(3)>=3){
                        if (puntajes.get(3)>=3){
                            puntajesObtenidos.removeIf(n-> n.equals(valor)
                                    &&contador[0]++<3);
                            puntajeTurno+=400;
                        }
                    }
                    break;
                case 5:
                    if (puntajes.get(4)>=1) {
                        if (puntajes.get(4) >= 3) {
                            puntajesObtenidos.removeIf(n -> n.equals(valor)
                                    && contador[0]++ < 3);
                            puntajeTurno += 500;
                            System.out.println("1");
                        }else{
                            puntajesObtenidos.remove(i);
                            puntajeTurno += 50;
                        }
                    }
                    break;
                case 6:
                    if (puntajes.get(5)>=3){
                        if (puntajes.get(5)>=3) {
                            puntajesObtenidos.removeIf(n -> n.equals(valor)
                                    && contador[0]++ < 3);
                            puntajeTurno += 600;
                        }
                    }
                    break;
            }
        }
    }
    // en caso de que alguien ya gano todos pueden tirar una ultima vez
    public void ultimaOportunidadWin(){
        int turnoActual = 1;
        for (int i = 0; i< numJugadores;i++) {
            if (turno != posicionGanador) {
                int seguirTirando = 1;
                while (seguirTirando == 1) {
                    ocultarDados();
                    int hotdice = 0;
                    tirarDados();
                    evaluarPosiblesDadosParaPuntuar();
                    if (verificarFarkled()) {
                        puntajeTurno = 0;
                        turnoActual = 2;
                    } else if (verificarHotDice()) {
                        hotdice = 1;
                        sumarHotDice();
                        System.out.println("Puntaje: " + puntajeTurno);
                        puntajes.clear();
                        puntajes = new ArrayList<>(Collections.nCopies(6, 0));
                        //inicializo el arraylist de dados con dados con valor 0
                        dados.clear();
                        dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                                .limit(6) // cuantos dados quiero guardar en el arraylist
                                .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
                        // inicializo arraylist de puntajes, donde nCopies recibe 6: tamaño, y 0: valor de cada posicion
                        puntajesObtenidos.clear();
                        puntajesPosibles.clear();
                        numDadosActual = 6;
                        numDados = 6;
                        seguirTirando = decidirSiSeguirTirando();
                        if (seguirTirando == 0) {
                            turnoActual = 2;
                        }
                    } else {
                        turnoActual = mostrarDadosPosiblesEnVentana();
                    }
                    // si ya no quiso tirar
                    if (turnoActual == 2) {
                        guardarPuntaje();
                        ocultarDados();
                        cambioDeTurno();
                        puntajes.clear();
                        puntajes = new ArrayList<>(Collections.nCopies(6, 0));
                        // inicializo el arraylist de dados con dados con valor 0
                        dados.clear();
                        dados = Stream.generate(() -> new Dado()) // el constructor Dado define el valor en 0 por default
                                .limit(6) // cuantos dados quiero guardar en el arraylist
                                .collect(Collectors.toCollection(ArrayList::new)); // convierto a arraylist
                        // inicializo arraylist de puntajes, donde nCopies recibe 6: tamaño, y 0: valor de cada posicion
                        puntajesObtenidos.clear();
                        puntajesPosibles.clear();
                        numDadosActual = 6;
                        numDados = 6;
                    } else if (turnoActual == 1) {
                        ocultarDados();
                        puntajesPosibles.clear();
                        puntajesObtenidos.clear();
                        puntajes.clear();
                        puntajes = new ArrayList<>(Collections.nCopies(6, 0));
                    }
                }
            }
        }
    }
}
