import java.sql.SQLOutput;
import java.util.Random;

public class Dado{
    private int valor;
    // constructor dado
    public Dado(){
        valor=0;
    }
    // turar dadi
    public void tirarDado(){
        Random rand = new Random();
        this.valor = rand.nextInt(6)+1;
    }
    // obtener valor
    public int getValor(){
        return valor;
    }
    // mostrar dado en consola
    public void mostrarDadoEnConsola(){
        System.out.println("Valor del dado: "+valor);
    }
}