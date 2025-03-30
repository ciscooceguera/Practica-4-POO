import java.sql.SQLOutput;
import java.util.Random;

public class Dado{
    private int valor;

    public Dado(){
        valor=0;
    }

    public int tirarDado(){
        Random rand = new Random();
        valor = rand.nextInt(6)+1;
        return valor;
    }

    public int getValor(){
        return valor;
    }

    public void mostrarDadoEnConsola(){
        System.out.println("Valor del dado: "+valor);
    }
}