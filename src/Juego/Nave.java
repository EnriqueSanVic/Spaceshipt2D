

package Juego;

import frameworkvideojuegos.Actor;
import frameworkvideojuegos.Galeria;
import frameworkvideojuegos.Sprite;
import frameworkvideojuegos.SpriteMovil;
import java.awt.Image;

/**
 *
 * @author Enrique Sánchez 
 */
public final class Nave extends SpriteMovil implements Actor{
    
    
    /*
        Sprite con sometido a incercia y rozamiento
    */

    private final String urlNave= "./imagenes/nave.png";
 
    private final String urlNaveRotadaTop= "./imagenes/naveRotadaTop.png";
    
    private final String urlNaveRotadaBott= "./imagenes/naveRotadaBott.png";
    
    private Image imagenPrincipal;
    
    private Image imagenRotadaTop;
    
    private Image imagenRotadaBott;
    
    private final int DELAY_DISPARO = 5;
    
    private int famesDelayDisparo = 0;
    
    private int correcionXCol = 0,  correcionYCol = 0;
    private int correcionWidthCol = 0,  correcionHeightCol = 0;
    
   private boolean 
            movTop = false,
            movBottom = false,
            movLeft = false,
            movRight = false,
            disparar = false;
    
    
    public Nave() {    
        
        this.setVerColider(false);
        
        //cargamos en memoria todas las imagenes
        imagenPrincipal = Galeria.getGaleria().getImagen(urlNave);
        
        imagenRotadaTop = Galeria.getGaleria().getImagen(urlNaveRotadaTop);
        
        imagenRotadaBott = Galeria.getGaleria().getImagen(urlNaveRotadaBott);
        
        //confirmamos la principal
        this.confirmarImagen(imagenPrincipal);
        
        this.setStep(3);
        
        this.setRozamiento(true);
        
        this.setColisionesEscena(true);
    }
    
    
    private void actualizarImagen(){
        
        correcionXCol = 0;
        correcionYCol = 0;
        correcionHeightCol=0;
        correcionWidthCol = 0;
        
        if(movTop){
            this.confirmarImagen(imagenRotadaTop);
        }else if(movBottom){
            this.confirmarImagen(imagenRotadaBott);
        }else{
            this.confirmarImagen(imagenPrincipal);
            
            //se aplica la conrreción del colider con esta imagen
            correcionYCol = 27;
            correcionHeightCol = -41;
        }
        
    }
    
    private void comprobarDisparo(){
    
        //si se cumplen las condiciones para disparar
        if(famesDelayDisparo <= 0 && disparar){
            
            crearDisparo();
            
            famesDelayDisparo = DELAY_DISPARO;
            
        }else{
            --famesDelayDisparo;
        }
        
    }
    
    private void actualizarColider() {
        
        this.setxCol(xCol + correcionXCol);
        this.setyCol(yCol + correcionYCol);
        this.setWidthCol(widthCol + correcionWidthCol);
        this.setHeightCol(heightCol + correcionHeightCol);
        
    }

    @Override
    public void actualizar() {
        
        actualizarImagen();
 
        super.actualizar();
        
        actualizarColider();
        
        comprobarDisparo();
    }
    
    
    private void crearDisparo(){
        
        Sprite.controlador.addSprite(new BalaLaser(xPuntDisparo(), yPuntDisparo()));
        
    }
    
    public int xPuntDisparo(){
        return super.x + super.width;
    }
    
    public int yPuntDisparo(){
        return super.y + (super.height/2);
    }
    
    //definimos la interfaz de movimiento
    public void movLeftAction(boolean act){
        movLeft = act;
        if(act) vX-= getStep();
    }
    
    public void movRightAction(boolean act){
        movRight = act;
        if(act) vX+= getStep();
    }
    
    public void movTopAction(boolean act){
        movTop = act;
        if(act) vY-= getStep();
    }
    
    public void movBottomAction(boolean act){
        movBottom = act;
        if(act) vY+= getStep();
    }


    public void disparar(boolean act) {
        disparar = act;
    }

    

    
    
    

    
}
