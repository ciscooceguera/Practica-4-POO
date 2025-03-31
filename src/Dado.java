import javax.swing.*;
import java.sql.SQLOutput;
import java.util.Random;

public class Dado{
    private int valor, xPosicion, yPosicion, tamañoCara, tamañoPunto;
    private Square cara;
    private Circle punto1, punto2, punto3, punto4, punto5, punto6, punto7;
    // constructor dado
    public Dado(){
        valor=0;
        xPosicion=100;
        yPosicion=100;
        tamañoCara=100;
        tamañoPunto=12;
        cara = new Square();
        punto1 = new Circle();
        punto2 = new Circle();
        punto3 = new Circle();
        punto4 = new Circle();
        punto5 = new Circle();
        punto6 = new Circle();
        punto7 = new Circle();
    }

    //Metodo que mueve el dado a cualquier posicion mandada como parametro
    public void mover(int x, int y){
        this.xPosicion=x;
        this.yPosicion=y;
    }

    //Metodo que oculta por completo todo el dado
    public void ocultar(){
        punto1.makeInvisible();
        punto2.makeInvisible();
        punto3.makeInvisible();
        punto4.makeInvisible();
        punto5.makeInvisible();
        punto6.makeInvisible();
        punto7.makeInvisible();
        cara.makeInvisible();
    }

    //Metodo que acomoda toda la cara del dado para que se vea bien definido y estetico.
    public void acomodar(){
        cara.changePosition(xPosicion, yPosicion);
        cara.changeSize(tamañoCara);
        punto1.changeSize(tamañoPunto);
        punto2.changeSize(tamañoPunto);
        punto3.changeSize(tamañoPunto);
        punto4.changeSize(tamañoPunto);
        punto5.changeSize(tamañoPunto);
        punto6.changeSize(tamañoPunto);
        punto7.changeSize(tamañoPunto);

        int delta=tamañoCara/4;

        punto1.changePosition(xPosicion+delta-tamañoPunto/2, yPosicion+delta-tamañoPunto/2);
        punto2.changePosition(xPosicion+delta-tamañoPunto/2, yPosicion+delta*2-tamañoPunto/2);
        punto3.changePosition(xPosicion+delta-tamañoPunto/2, yPosicion+delta*3-tamañoPunto/2);
        punto4.changePosition(xPosicion+delta*2-tamañoPunto/2, yPosicion+delta*2-tamañoPunto/2);
        punto5.changePosition(xPosicion+delta*3-tamañoPunto/2, yPosicion+delta-tamañoPunto/2);
        punto6.changePosition(xPosicion+delta*3-tamañoPunto/2, yPosicion+delta*2-tamañoPunto/2);
        punto7.changePosition(xPosicion+delta*3-tamañoPunto/2, yPosicion+delta*3-tamañoPunto/2);
    }

    // turar dado
    public void tirar(){
        int yesNoJugarResponse = 0;
        yesNoJugarResponse = JOptionPane.showConfirmDialog(null,"¿Quieres lanzar dado?:","Dado",JOptionPane.DEFAULT_OPTION);
        Random rand = new Random();
        this.valor = rand.nextInt(6)+1;
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
                    punto4.makeVisible();
                    break;
                case 2:
                    punto1.makeVisible();
                    punto7.makeVisible();
                    break;
                case 3:
                    punto2.makeVisible();
                    punto4.makeVisible();
                    punto6.makeVisible();
                    break;
                case 4:
                    punto1.makeVisible();
                    punto3.makeVisible();
                    punto5.makeVisible();
                    punto7.makeVisible();
                    break;
                case 5:
                    punto1.makeVisible();
                    punto3.makeVisible();
                    punto4.makeVisible();
                    punto5.makeVisible();
                    punto7.makeVisible();
                    break;
                case 6:
                    punto1.makeVisible();
                    punto2.makeVisible();
                    punto3.makeVisible();
                    punto5.makeVisible();
                    punto6.makeVisible();
                    punto7.makeVisible();
                    break;
            }
        }
    }
}