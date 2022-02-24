

package frameworkvideojuegos.ReproductorSonido;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Hilo de reproducción de un fichero de audio en bucle.
 *
 * Ejecutar el Thread para iniciar la reproducción y matar el hilo para pararlo.
 * 
 * @author Enrique Sánchez 
 */
public final class ReproductorContinuo extends Thread{

    private File file;
    
    private Clip clip;
    
    private String id;
    
    /**
     * Constructor
     * 
     * @param path ruta del fichero de audio. 
     */
    public ReproductorContinuo(String path) {
        this.id = path;
        this.file = new File(path);
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void run() {
        
        if (file.exists()) {

            try {

                clip = AudioSystem.getClip();

                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

                clip.open(inputStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY); //Para que se repita la cancion infinitamente mientras el juego este abierto.

                clip.start();

            } catch (Exception ex) {
                
            }

        }
        
    }

   
    public void matar() {
        
        if(clip != null){
            clip.stop();
            clip = null;
        }
        
    }
    
    
    @Override
    public String toString() {
        return "ReproductorContinuo" + id;
    }

    
}
