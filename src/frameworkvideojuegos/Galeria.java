

package frameworkvideojuegos;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Enrique Sánchez 
 */
public class Galeria {

    
    //Singelton 
    private static Galeria galeria;
    
    //Hashmap para guardar la imñagenes en ram una vez y no tener que cargarlas del disco constantemente
    private HashMap<String, Image> guardado = new HashMap<String, Image>();
    
    private Galeria() {
    }
     
    
    //solo se puede acceder al unico objeto por este método
    public static Galeria getGaleria(){
        
        if(galeria == null) galeria = new Galeria();
        
        return galeria;
        
    }
      
    
    //Método para cargar una imagen
    public Image getImagen(String url){
    
        
        //si el hasmap contiene la key de la ruta se retorna su valor
        //significa que ya lo tenía guardado
        if(guardado.containsKey(url)){
            return guardado.get(url);
        }
        
        //si se pasa de esta linea significa que no está guardado y hay que recuperarlo
        //se carga la imagen del disco a la ram
        Image nuevaImagen = cargarImagen(url);
        
        //se guarda en el hashmap 
        guardado.put(url, nuevaImagen);
        
        //se retorna
         return nuevaImagen; 
    }
    
    //Método para cargar la imagen desde una ruta con File
    private Image cargarImagen(String ruta){
        
        Image img = null;
        File fichero = new File(ruta);
        
        try{
            
            img = ImageIO.read(fichero);
        
        }catch(Exception ex){
        
        }
        
        return img;
    }
    
    //mñetodo para redimensionar una imagen
    public Image redimensionarImg(Image img, int width, int height){
       Image imagen;
       imagen=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
       return imagen;
   }

}
