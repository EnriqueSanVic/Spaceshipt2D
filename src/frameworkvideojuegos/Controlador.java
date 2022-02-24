

package frameworkvideojuegos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * Cosas a hacer
 * 
 * Quitar las dependencias del controlador del mapeo de teclas para definirlas en las clases hijas
 * 
 * 
 * @author Enrique Sánchez 
 */
public abstract class Controlador extends Thread implements KeyListener{

    protected HashMap<Sprite,Sprite> sprites;
     
    private HashMap<Sprite,Sprite> copiaIterable;
    
    private Iterator<Sprite> iter;
    
    private final Vista vista;
    private long FPS = 60;
    
    //doble buffer
    private BufferStrategy buffer;
    private Graphics2D graphics;
    
    protected Actor actor;
        
    protected boolean fin=false;
   

    public Controlador() {
        
        this.setPriority(Thread.MAX_PRIORITY);
        
        //se le asigna a la clase sprite esta instancia del controlador para que todos tengan el mismo controlador
        Sprite.controlador = this;
        
        sprites = new HashMap<Sprite,Sprite>();
        
        copiaIterable = new HashMap<Sprite,Sprite>();
                
        vista = new Vista();
        
        vista.addKeyListener(this);
        
        vista.createBufferStrategy(2);
        
        buffer = vista.getBufferStrategy();
        
        graphics = (Graphics2D) buffer.getDrawGraphics();
        
    }

    @Override
    public void run() {
        
        //un segundo en nanosegundos entre el numero de ftp para saber cuanto tiene que durar cada frame
        long tFrame = 1000000000/FPS;
        long tIni, tFin;
        long tActivo, sleepTime;
        double deltaTime;
        
        //Bucle de renderizado
        do{
            
            
            tIni = System.nanoTime();
            
            actualizar();
            dibujar();
            
            tFin = System.nanoTime();
            
            tActivo = tFin - tIni;
            
            sleepTime =  (tFrame - tActivo)/1000000;
            
            if(sleepTime > 0){
                dormir(sleepTime);
            }
            
        }while(!fin);
        
    }
    
    protected void actualizar(){
    
        mandarAccionesActor();

        /*
            Realizo una copia del hashmap para iterarla en la actualización y eviar errores 
            de tipo ConcurrentModificationException por modificaciones del iterador
        */
        copiaIterable.clear();
        
        copiaIterable.putAll(sprites);
        
        iter = copiaIterable.values().iterator();  
        
        while (iter.hasNext()) {
            
            iter.next().actualizar();
            
        }
        
    }
    
    
    
    
    
    private void dibujar(){


        dibujarFondo(graphics);
        

        iter = sprites.values().iterator();  
        
        while (iter.hasNext()) {  
            
            iter.next().dibujar(graphics);
            
        }
        
        dibujarInterfazJuego(graphics);
        
        buffer.show();
        
    }
    
    private void dormir(long sleepTime) {

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {

        }
    }
    
    public abstract void reiniciar();
    
    public void limpiarSprites(){
        this.sprites.clear();
    }
    
    public void limpiarActores(){
        actor = null;
    }
    
    public void addSprite(Sprite sp){
        sprites.put(sp, sp);
    }
    
    public void deleteSprite(Sprite sp){
        sprites.remove(sp);
    }
    
    public int getWidth(){
        return vista.getWidth();
    }
    
    public int getHeight(){
        return vista.getHeight();
    }

    

    public void addActor(Actor actor) {
        this.actor = actor;
    }
    
    public abstract void mandarAccionesActor();
    

    @Override
    public abstract void keyPressed(KeyEvent ke);

    @Override
    public abstract void keyReleased(KeyEvent ke);
    
    @Override
    public abstract void keyTyped(KeyEvent ke);

    public abstract void dibujarFondo(Graphics2D gr);
    
    public SpriteFondoEscena crearFondo(String url){
        
        
        Image imagenFondo = Galeria.getGaleria().redimensionarImg(
                Galeria.getGaleria().getImagen(url),
                this.getWidth(),
                this.getHeight()
        );
        
        return new SpriteFondoEscena(imagenFondo);
        
    }

    
    //metodo para dibujar la interfaz de juego, tiene que definirse por completo
    public abstract void dibujarInterfazJuego(Graphics2D gr);

 
    
}
