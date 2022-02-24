

package Juego;

import frameworkvideojuegos.Galeria;
import frameworkvideojuegos.ReproductorSonido.ReproductorUnaVez;
import frameworkvideojuegos.Sprite;
import frameworkvideojuegos.SpriteMovil;

/**
 *
 * @author Enrique Sánchez 
 */
public class BalaLaser extends SpriteMovil{
    
    private final String URL_SONIDO_DISPARO = "./sound/laser.aiff";
    
    private final String URL_LASER= "./imagenes/laser.png";
    
    private final int MARGEN_DESTRUCCION_FUERA_ESCENARIO = 100;
    
     public BalaLaser(int x, int y) {    

        this.setVerColider(false);
        
        //cargamos en memoria la imagen
        this.confirmarImagen(Galeria.getGaleria().getImagen(URL_LASER));
        
        this.setStep(10);
        
        this.setRozamiento(false);
        
        this.setColisionesEscena(false);
        
        this.setvX(50);
        
        super.x = x;
        super.y = y;
        
        new ReproductorUnaVez(URL_SONIDO_DISPARO).start();
    }
     
     

    @Override
    public void actualizar() {
        
        //si la bala se sale del límite right del mapa + 100 px se destruye
        if(this.x > Sprite.controlador.getWidth() + MARGEN_DESTRUCCION_FUERA_ESCENARIO){
            this.activo = false;
            Sprite.controlador.deleteSprite(this);
        }
        
        super.actualizar(); 
    }
     
     
    

}
