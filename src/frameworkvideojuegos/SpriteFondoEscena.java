

package frameworkvideojuegos;

import Juego.Mundo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Enrique Sánchez 
 */
public class SpriteFondoEscena {
    
    private final int DEALY_BASE = 3;
    
    private final int avance = 4;
    
    public int x,y;
    
    public int width, height;
    
    private Image imagen;
    
    private int delay;

    public SpriteFondoEscena(Image imagen) {
        this.imagen = imagen;
        
        width = this.imagen.getWidth(null);
        height = this.imagen.getHeight(null);
        
        delay = DEALY_BASE;
    }
    
    public void dibujar(Graphics gr){
        
        gr.drawImage(imagen, x, y, null);
        
    }
     
    public void actualizar(){
    
        if(--delay <= 0){
            
            delay = DEALY_BASE;
            
            x-= (avance + (int)(avance*Mundo.factorRapidez));
            
            if((x + width) < 0){
                x = width;
            }
        
        }
        
    }
    
    
    
}
