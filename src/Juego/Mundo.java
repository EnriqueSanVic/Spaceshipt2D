

package Juego;

import frameworkvideojuegos.Sprite;
import frameworkvideojuegos.Controlador;
import frameworkvideojuegos.ReproductorSonido.ReproductorContinuo;
import frameworkvideojuegos.ReproductorSonido.ReproductorUnaVez;
import frameworkvideojuegos.SpriteFondoEscena;
import frameworkvideojuegos.SpriteTexto;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enrique Sánchez 
 */
public class Mundo extends Controlador{

    private final String URL_IMAGEN_FONDO = "./imagenes/fondoPlaneta.jpg";
    
    private final String URL_SONIDO_FALLO= "./sound/fallo.aiff";
    
    private final String URL_MUSICA= "./sound/temas.wav";
    
    private Color colorFondo = new Color(47,33,45);
    
    private final int DELAY_METEORITO = 40;
    
    private int delayMeteorito = DELAY_METEORITO;
    
    private final int MARGEN_ESCENA_GENERAR_METEORITOS = 100;
    
    private HashMap<Sprite,Sprite> copiaIterableColisiones;
    
    private Iterator<Sprite> iterB;
    
    private Iterator<Sprite> iterC;
    
    private Sprite bala, meteorito;
    
    private SpriteTexto textoPuntuacion;
    
    private SpriteFondoEscena fondoA, fondoB;
    
    private Thread hiloFondo;
    
    private ReproductorContinuo hiloMusical;
    
    private boolean usarFondo = false;
    
    private int puntos;
    
    private final float RAPIDEZ_BASE = 0.1f;
    
    private final long PERIODO_AUMENTO = 5000000000l;
    
    public static float factorRapidez;
    
    private long tiempoTranscursoInicio, tiempoTranscursoFin;
    
    private final int VEL_METEORITO_MAX = 8;
        
    private final int VEL_METEORITO_MIN = 6;
    
    //booleanos de presión de botón
    private boolean 
            btnTop = false,
            btnBottom = false,
            btnLeft = false,
            btnRight = false,
            btnDisparar = false;
    
    
    private Random rand = new Random();
    
     public Mundo() {
         
         super();
         
        copiaIterableColisiones = new HashMap<Sprite,Sprite>();

        puntos = 0;
        
        confFondo();
        
        usarFondo = true;
        
        factorRapidez = RAPIDEZ_BASE;
        
        textoPuntuacion = crearSpritePuntuacion();
        
        super.addActor(crearNave());

        tiempoTranscursoInicio = System.nanoTime();

        hiloMusical = new ReproductorContinuo(URL_MUSICA);
 
        hiloMusical.start();
        
        super.start();

    }
     
     
     private Nave crearNave(){
     
        Nave nave = new Nave();
        
        super.addSprite(nave);

        nave.setX(100);
        nave.setY((super.getHeight()/2) - nave.getHeightCol());
        
        return nave;
        
     }

    @Override
    protected void actualizar() {
        
        comprobarRapidez();
        
        --delayMeteorito;
        
        if(delayMeteorito <= 0){
            generarMeteorito();
            delayMeteorito = DELAY_METEORITO;
        }
        
        
        comprobarColisiones();
        
        super.actualizar(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    private void comprobarRapidez() {
        
        tiempoTranscursoFin = System.nanoTime();
        
        if(tiempoTranscursoFin - tiempoTranscursoInicio >= PERIODO_AUMENTO){
        
            tiempoTranscursoInicio = tiempoTranscursoFin;
            
            factorRapidez += RAPIDEZ_BASE;
            
        }
        
    }
    
    

    private void generarMeteorito() {
        
        int x, y, velX;
        
        
        
        x = super.getWidth() + MARGEN_ESCENA_GENERAR_METEORITOS;
        
        y = rand.nextInt(super.getHeight() + 300) - 150;
        
        //decidimos aleatoriamente de momento la velocidad en X
        velX = (int) (rand.nextInt((int) (VEL_METEORITO_MAX + (VEL_METEORITO_MAX*factorRapidez))) + (VEL_METEORITO_MIN + (VEL_METEORITO_MAX*factorRapidez)));
        
        super.addSprite(new Meteorito(x,y,(-velX)));
        
    }

    private void comprobarColisiones() {
        
        
        colisionesBalas();
        colisionesActores();
        
        
    }

    private void colisionesActores() {

        //miramos que sean de tipo Nave
        if (actor instanceof Nave) {
            //se recorren todos los sprites
            for (HashMap.Entry<Sprite, Sprite> j : sprites.entrySet()) {
                //si es un meteorito
                if (j.getValue() instanceof Meteorito) {

                    if (Sprite.colision((Sprite) actor, j.getValue())) {

                        reiniciar();

                        break;
                    }

                }

            }

        }

    }

    private void colisionesBalas() {

        copiaIterableColisiones.clear();

        copiaIterableColisiones.putAll(sprites);

        iterB = copiaIterableColisiones.values().iterator();

        while (iterB.hasNext()) {

            bala = iterB.next();

            if (bala instanceof BalaLaser) {

                iterC = copiaIterableColisiones.values().iterator();

                while (iterC.hasNext()) {

                    meteorito = iterC.next();

                    //si es un meteorito
                    if (meteorito instanceof Meteorito) {
                        //si la bala y el meteorito colisionan
                        if (Sprite.colision(bala, meteorito)) {
                            //se borran del array original

                            //siempre se borra la bala
                            super.deleteSprite(bala);

                            if (((Meteorito) meteorito).golpeado()) {
                                super.deleteSprite(meteorito);
                                ++puntos;
                                actualizarPuntuacion();
                            }
                        }
                    }
                }

            }

        }

    }

    @Override
    public void reiniciar() {
        
        new ReproductorUnaVez(URL_SONIDO_FALLO).start();
        
        super.limpiarSprites();
        
        super.limpiarActores();
        
        factorRapidez = RAPIDEZ_BASE;
        
        puntos = 0;
        
        actualizarPuntuacion();
        
        super.addActor(crearNave());

        
    }
     
    
    public void mandarAccionesActor(){
        
        actor.movTopAction(btnTop);
        actor.movBottomAction(btnBottom);
        actor.movLeftAction(btnLeft);
        actor.movRightAction(btnRight);
        actor.disparar(btnDisparar);
    }
    
    
    @Override
    public void keyPressed(KeyEvent ke) {
        
        
        
        switch(ke.getKeyCode()){
            
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                btnLeft = true;
                break;
            
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                btnTop = true;
                break;
            
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                btnRight = true;
                break;
            
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                btnBottom = true;
                break;
                
            case KeyEvent.VK_SPACE:
                btnDisparar = true;
                break;
                
            case KeyEvent.VK_ESCAPE:
                super.fin = true;
                System.exit(0);
                break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
        switch(ke.getKeyCode()){
            
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                btnLeft = false;
                break;
            
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                btnTop = false;
                break;
            
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                btnRight = false;
                break;
            
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                btnBottom = false;
                break;
                
            case KeyEvent.VK_SPACE:
                btnDisparar = false;
                break;
                
        }
        
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    private SpriteTexto crearSpritePuntuacion() {
        
        return new SpriteTexto(70, "0", Color.orange, (super.getWidth() - 150), 60);
   
    }
     
    
    private void actualizarPuntuacion(){
        textoPuntuacion.setTexto(String.valueOf(puntos));
    }

    @Override
    public void dibujarFondo(Graphics2D gr) {
        
        if(!usarFondo){
        
            gr.setColor(colorFondo);
            gr.fillRect(0, 0, super.getWidth(), super.getHeight());
        
        }else{
            
            fondoA.dibujar(gr);
            fondoB.dibujar(gr);

        }  
        
    }

    private void actualizarFondos() {
        
        fondoA.actualizar();
        fondoB.actualizar();
        
    }

    private void confFondo() {
        
        fondoA = super.crearFondo(URL_IMAGEN_FONDO);
        fondoB = super.crearFondo(URL_IMAGEN_FONDO);
        
        fondoA.x = 0;
        fondoA.y = 0;
        
        fondoB.x = fondoA.width;
        fondoB.y = 0;
        
        //Hilo para controlar la actualización del fondo
        hiloFondo = new Thread(new Runnable() {
            @Override
            public void run() {
                
                while(true){
                    actualizarFondos();
                    dormir();
                }
                
            }
            
            private void dormir(){
                try {
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Mundo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        hiloFondo.start();
        
    }

    @Override
    public void dibujarInterfazJuego(Graphics2D gr) {
        
        textoPuntuacion.dibujar(gr);
        
    }

    

    

    
}
