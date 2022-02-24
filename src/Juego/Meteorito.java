

package Juego;

import frameworkvideojuegos.Galeria;
import frameworkvideojuegos.Sprite;
import frameworkvideojuegos.SpriteMovil;
import java.awt.Image;
import java.util.Random;

/**
 *
 * @author Enrique Sánchez 
 */
public class Meteorito extends SpriteMovil{

    private Random rand = new Random();
    
    private final short VIDAS_ESTANDAR = 3;
    
    private short vidas;
    
    private final static Image[] IMAGENES= new Image[]{
        Galeria.getGaleria().getImagen("./imagenes/met1A.png"),
        Galeria.getGaleria().getImagen("./imagenes/met1B.png"),
        Galeria.getGaleria().getImagen("./imagenes/met1C.png"),
        Galeria.getGaleria().getImagen("./imagenes/met2A.png"),
        Galeria.getGaleria().getImagen("./imagenes/met2B.png"),
        Galeria.getGaleria().getImagen("./imagenes/met3A.png"),
        Galeria.getGaleria().getImagen("./imagenes/met3B.png"),
        Galeria.getGaleria().getImagen("./imagenes/met4A.png"),
        Galeria.getGaleria().getImagen("./imagenes/met4B.png"),
        Galeria.getGaleria().getImagen("./imagenes/met5A.png")
    };
    
    private final int MARGEN_DESTRUCCION_FUERA_ESCENARIO = 1000;
    
     public Meteorito(int x, int y, int velX) { 
         
        this.setVerColider(false);
         
         
        this.vidas = VIDAS_ESTANDAR;
         
         //cargamos de manera aleatoria una imagen al prite
        //cargamos en memoria la imagen
        
        this.confirmarImagen(IMAGENES[rand.nextInt(IMAGENES.length)]);
        
        this.setStep(10);
        
        this.setRozamiento(false);
        
        this.setColisionesEscena(false);
        
        this.setvX(velX);
        
        super.setX(x);
        super.setY(y);
    }
     
     public boolean golpeado(){
        //se restan vidas y si es menor o igual a 0 se retorna true
        return (--vidas <= 0);
         
     }

    @Override
    public void actualizar() {
        
        //si la bala se sale del límite right del mapa + 100 px se destruye
        if(this.x < 0 - MARGEN_DESTRUCCION_FUERA_ESCENARIO){
            this.activo = false;
            Sprite.controlador.deleteSprite(this);
        }
        
        super.actualizar(); 
    }
}
