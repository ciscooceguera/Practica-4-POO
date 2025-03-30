import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // inicializo la bandera para el control del juego
        int yesNoJugarResponse = 0;
        // ciclo while para que se pueda jugar siempre que asi se desee
        while (yesNoJugarResponse == 0){
            // pregunto si quiere jugar
            yesNoJugarResponse = JOptionPane.showConfirmDialog(null,"Quieres jugar?:","",JOptionPane.YES_NO_OPTION);
            // switch
            switch(yesNoJugarResponse){
                // caso SI quiere jugar
                case 0:
                    // solicito el numero de jugadores
                    String numJugadoresStr = JOptionPane.showInputDialog("Ingrese numero de jugadores: ");
                    int numJugadores = Integer.parseInt(numJugadoresStr);
                    // solicito el puntaje minimo para skipear por ronda
                    String puntajeSkipStr = JOptionPane.showInputDialog("Ingrese puntuacion minima para pasar turno: ");
                    int puntajeSkip = Integer.parseInt(puntajeSkipStr);
                    // solicito el puntaje para ganar
                    String puntajeWinStr = JOptionPane.showInputDialog("Ingrese numero de jugadores: ");
                    int puntajeWin = Integer.parseInt(puntajeWinStr);
                    // declaro e inicializo objeto Farkle
                    Farkle farkle = new Farkle(numJugadores,puntajeSkip,puntajeWin);
                    break;
                // caso NO quiere jugar
                case 1:
                    break;
            }
        }
    }
}