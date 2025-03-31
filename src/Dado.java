import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Dado{
    private int valor, xPosicion, yPosicion, tamañoCara, tamañoPunto;
    private Square cara;
    private ArrayList<Circle> puntos;
    private Circle punto1, punto2, punto3, punto4, punto5, punto6, punto7;
    // constructor dado
    public Dado(){
        valor=0;
        xPosicion=100;
        yPosicion=100;
        tamañoCara=100;
        tamañoPunto=12;
        cara = new Square();
        puntos=new ArrayList<>();
        inicializarPuntos();
    }
    //Metodo que mueve el dado a cualquier posicion mandada como parametro
    public void mover(int x, int y){
        this.xPosicion=x;
        this.yPosicion=y;
    }

    //Metodo que inicializa el ArrayList de puntos
    public void inicializarPuntos(){
        for(int i=0;i<7;i++){
            Circle punto=new Circle();
            puntos.add(punto);
        }
    }

    //Metodo que oculta por completo todo el dado
    public void ocultar(){
        puntos.stream().forEach(punto->punto.makeInvisible());
        cara.makeInvisible();
    }

    //Metodo que acomoda toda la cara del dado para que se vea bien definido y estetico.
    public void acomodar(){
        cara.changePosition(xPosicion, yPosicion);
        cara.changeSize(tamañoCara);
        puntos.stream().forEach(punto->punto.changeSize(tamañoPunto));

        int delta=tamañoCara/4;
        puntos.get(0).changePosition(xPosicion+delta-tamañoPunto/2, yPosicion+delta-tamañoPunto/2);
        puntos.get(1).changePosition(xPosicion+delta-tamañoPunto/2, yPosicion+delta*2-tamañoPunto/2);
        puntos.get(2).changePosition(xPosicion+delta-tamañoPunto/2, yPosicion+delta*3-tamañoPunto/2);
        puntos.get(3).changePosition(xPosicion+delta*2-tamañoPunto/2, yPosicion+delta*2-tamañoPunto/2);
        puntos.get(4).changePosition(xPosicion+delta*3-tamañoPunto/2, yPosicion+delta-tamañoPunto/2);
        puntos.get(5).changePosition(xPosicion+delta*3-tamañoPunto/2, yPosicion+delta*2-tamañoPunto/2);
        puntos.get(6).changePosition(xPosicion+delta*3-tamañoPunto/2, yPosicion+delta*3-tamañoPunto/2);
    }

    // turar dado
    public void tirar(){
        int yesNoJugarResponse = 0;
        yesNoJugarResponse = JOptionPane.showConfirmDialog(null,"¿Quieres lanzar dado?:","Dado",JOptionPane.DEFAULT_OPTION);
        Random rand = new Random();
        this.valor = rand.nextInt(6)+1;
    }
    public void setValor(int valor){
        this.valor=valor;
    }
    // obtener valor
    public int getValor(){
        return valor;
    }

    //Getter de posicion en X
    public int getXPosicion(){
        return xPosicion;
    }

    //Getter de posicion en Y
    public int getYPosicion(){
        return yPosicion;
    }

    //Getter del tamaño de la cara
    public int getTamañoCara(){
        return cara.getSize();
    }

    // mostrar dado en consola
    public void mostrarEnConsola(){
        System.out.println("Valor del dado: "+valor);
    }

    //Metodo que muestra en pantalla el dado con su respectivo valor
    public void mostrarEnCanvas(int valor){
        acomodar();
        if(valor>=1 && valor<=6){
            ocultar();
            cara.makeVisible();
            switch(valor){
                case 1:
                    puntos.get(3).makeVisible();
                    break;
                case 2:
                    puntos.get(0).makeVisible();
                    puntos.get(6).makeVisible();
                    break;
                case 3:
                    puntos.get(1).makeVisible();
                    puntos.get(3).makeVisible();
                    puntos.get(5).makeVisible();
                    break;
                case 4:
                    puntos.get(0).makeVisible();
                    puntos.get(2).makeVisible();
                    puntos.get(4).makeVisible();
                    puntos.get(6).makeVisible();
                    break;
                case 5:
                    puntos.get(0).makeVisible();
                    puntos.get(2).makeVisible();
                    puntos.get(3).makeVisible();
                    puntos.get(4).makeVisible();
                    puntos.get(6).makeVisible();
                    break;
                case 6:
                    puntos.get(0).makeVisible();
                    puntos.get(1).makeVisible();
                    puntos.get(2).makeVisible();
                    puntos.get(4).makeVisible();
                    puntos.get(5).makeVisible();
                    puntos.get(6).makeVisible();
                    break;
            }
        }
    }
}