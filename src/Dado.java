import java.util.Random;
// clase dado se encargara de toda accion que implique a un dado
public class Dado{
    private int valor;
    // constructor
    public Dado(){
        // inicializo valor en 0
        this.valor = 0;
    }
    // genera un numero random y lo guarda en el atributo
    public int tirarDado(){
        Random rnd = new Random();
        this.valor = rnd.nextInt(6)+1;
        return this.valor;
    }
}