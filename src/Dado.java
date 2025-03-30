import java.util.Random;

public class Dado{
    private int valor;
    public Dado(){
        this.valor = 0;
    }
    public int tirarDado(){
        Random rnd = new Random();
        this.valor = rnd.nextInt(6)+1;
        return this.valor;
    }
}