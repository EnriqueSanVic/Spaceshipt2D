

package frameworkvideojuegos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * 
 * Por defecto las medidas del colider son las medidas de la imagen.
 *
 * @author Enrique Sánchez 
 */
public abstract class Sprite {
    
    public static Controlador controlador;
    
    protected int x, y; 
    
    protected int width, height;
    
    protected int xCol, yCol;
    
    protected int widthCol, heightCol;
    
    private Image imagen = null;
    
    protected boolean activo;
    protected boolean verColider = false;
    protected boolean eliminar;
    protected boolean colisionar;

    public Sprite() {
        
        activo = true;
        eliminar = false;
        colisionar = true;
        
    }
    
    
    
    public void confirmarImagen(Image imagen){
        this.imagen = imagen;
        this.setWidth(this.imagen.getWidth(null));
        this.setHeight(this.imagen.getHeight(null));
    }
    
    
    
    public abstract void actualizar();
    
    public void dibujar(Graphics gr){
        
        if(activo){       
            gr.drawImage(imagen, x, y, null);       
        }
        
        if (verColider) {

            Graphics2D g2d = ((Graphics2D) gr);
            
            g2d.setColor(Color.YELLOW);
            

            System.out.println(xCol + " " +  yCol + " " + " " + widthCol + " " +  heightCol);
            g2d.drawRect(xCol, yCol, widthCol, heightCol);
            
           
        }
        
    }
    
    
    
    public int getLimLeft(){
        return xCol;
    }
    
    public int getLimRight(){
        return  xCol + widthCol;
    }
    
    public int getLimTop(){
        return yCol;
    }
    
    public int getLimBottom(){
        return yCol + heightCol;
    }
    
    
    
    public void posicionarAleatorio(){
        
        Random rd = new Random();
        
        this.setX(rd.nextInt( (controlador.getWidth() + 1) - width));
        this.setY(rd.nextInt( (controlador.getHeight() + 1) - height));
        
    }

    
    /*
        Por defecto las medidas del colider son las medidas de la imagen.
    */
    public void setX(int x) {
        this.x = x;
        this.xCol = x;
    }

    public void setY(int y) {
        this.y = y;
        this.yCol = y;
    }

    public void setWidth(int width) {
        this.width = width;
        this.widthCol = width;
    }

    public void setHeight(int height) {
        this.height = height;
        this.heightCol = height;
    }

    public int getxCol() {
        return xCol;
    }

    public void setxCol(int xCol) {
        this.xCol = xCol;
    }

    public int getyCol() {
        return yCol;
    }

    public void setyCol(int yCol) {
        this.yCol = yCol;
    }

    public int getWidthCol() {
        return widthCol;
    }

    public void setWidthCol(int widthCol) {
        this.widthCol = widthCol;
    }

    public int getHeightCol() {
        return heightCol;
    }

    public void setHeightCol(int heightCol) {
        this.heightCol = heightCol;
    }

    public void setVerColider(boolean verColider) {
        this.verColider = verColider;
    }
    
    

    public boolean isActivo() {
        return activo;
    }

    public boolean isEliminar() {
        return eliminar;
    }
    
    
    public static boolean colision(Sprite sp1,Sprite sp2) {
        
        //si con colisionables
        if(sp1.colisionar && sp2.colisionar){
            
            int w1,h1,w2,h2,x1,y1,x2,y2;
            boolean hayColision=false;

            x1=sp1.xCol; // posicion X del sprite 1
            y1=sp1.yCol; // posicion Y del sprite 1
            w1=sp1.widthCol; // ancho del sprite 1
            h1=sp1.heightCol; // altura del sprite 1

            x2=sp2.xCol; // posicion X del sprite 2
            y2=sp2.yCol; // posicion Y del sprite 2
            w2=sp2.widthCol; // ancho del sprite 2
            h2=sp2.heightCol; // altura del sprite 2

            //se mira si los coliders están superpuestos en algún punto
            if (((x1+w1)>x2) && ((y1+h1)>y2) && ((x2+w2)>x1) && ((y2+h2)>y1)) 
                hayColision=true;

            return hayColision;
        }
        
        return false;

    }
    
    
    
}
