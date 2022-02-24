

package frameworkvideojuegos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Enrique Sánchez 
 */
public class SpriteTexto extends Sprite{

    private int tamanio;
    private String texto;
    private Color color;
    private Font fuente;

    public SpriteTexto(int tamanio, String texto, Color color, int x, int y) {
        this.tamanio = tamanio;
        this.texto = texto;
        this.color = color;
        this.x = x;
        this.y = y;
        
        fuente =new Font("Arial",Font.BOLD,tamanio);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    
    @Override
    public void actualizar() {
 
    }

    @Override
    public void dibujar(Graphics gr) {
        
        gr.setColor(color);
        gr.setFont(fuente);
        gr.drawString(texto, x, y);     
        
    }
    
    
    

}
