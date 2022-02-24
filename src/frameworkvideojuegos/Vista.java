

package frameworkvideojuegos;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
 *
 * @author Enrique Sánchez 
 */
public class Vista extends JFrame{

    
    private int width, height;
    
    public Vista(){
        
        pantallaCompleta();
        
        setVisible(true);
        
       
        
    }
    
    

    private void pantallaCompleta(){
    
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Rectangle screenRect = gc.getBounds();
        width=screenRect.width;
        height=screenRect.height;
        setUndecorated(true);
        setResizable(false);
        setBounds(0,0,width,height);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
    
}
