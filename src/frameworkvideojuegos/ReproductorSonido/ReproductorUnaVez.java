

package frameworkvideojuegos.ReproductorSonido;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Hilo de reproducción de un fichero de audio de una sola ejecución.
 * 
 * Ejecutar el Thread para iniciar la reproducción.
 * 
 * el hilo se destruye solo pasado un tiempo suficiente para la ejecución del audio.
 *
 * @author Enrique Sánchez 
 */
public final class ReproductorUnaVez extends Thread{

    
    private AudioInputStream audio;

    private long duracion;
    
    private Clip reproductor;
    
    
    private String id;
    
    public ReproductorUnaVez(String path) {
        
        this.id = path;
                
        this.setPriority(Thread.MAX_PRIORITY);
        
        try {
            
            audio = AudioSystem.getAudioInputStream(new File(path));
            duracion = audio.getFrameLength()/2;
   
                
        }catch(Exception ex){

        }
        
    }

    

    @Override
    public void run() {
        
        try {
            
            reproductor = AudioSystem.getClip();
            
            reproductor.open(audio);
            
            reproductor.start();
            
            Thread.sleep(duracion);
            
            reproductor.stop();
            
            reproductor.flush();
            
            reproductor.close();
            
            audio.close();
            
            
        } catch (Exception ex) {
            //si se mata el hilo va a saltar esta excepcion por el sleep para la reproducción
        }
        
        reproductor = null;
        audio= null;

        
    }
    

    public void matar() {
        
        try {
            
            reproductor.stop();
            
            reproductor.flush();
            
            reproductor.close();
            
            audio.close();
            
            
        } catch (Exception ex) {
            
        }
        
        reproductor = null;
        audio= null;
        
    }

    @Override
    public String toString() {
        return "ReproductorUnaVez " + id;
    }

    
    
    
}
