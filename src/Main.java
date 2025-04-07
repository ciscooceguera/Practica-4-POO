import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // inicializo la bandera para el control del juego
        int yesNoJugarResponse = 0;
        // ciclo while para que se pueda jugar siempre que asi se desee
        while (yesNoJugarResponse != 2){
            // inicializo objeto de botones
            Object[] botones = {"Jugar","Creditos","Salir"};
            // pregunto si quiere jugar

            yesNoJugarResponse = JOptionPane.showOptionDialog(null,
           
                    
                    "Seleccione la opcion.",
                    "Pregunta",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    botones,
                    botones[0]);
            // switch
            switch(yesNoJugarResponse){
                // caso SI quiere jugar

                case 0:
                    // solicito el numero de jugadores
                    int numJugadores = 0;
                    while (numJugadores<2) {
                        String numJugadoresStr = JOptionPane.showInputDialog("Ingrese numero de jugadores: ");
                        numJugadores = Integer.parseInt(numJugadoresStr);
                    }
                    // solicito el puntaje para ganar
                    String puntajeWinStr = JOptionPane.showInputDialog("Ingrese el puntaje para ganar: ");
                    int puntajeWin = Integer.parseInt(puntajeWinStr);
                    // declar
                    // o e inicializo objeto Farkle
                    Farkle farkle = new Farkle(numJugadores,puntajeWin);
                    farkle.controlarFlujoDelJuego();
                    break;
                // caso NO quiere jugar

                case 1:
                    JOptionPane.showMessageDialog(null, "Creado por: \nFrancisco Oceguera\nSuffo Peimbert");
                    break;
                case 2:
                    System.exit(0);
                    break;
            }


        }
    }
}