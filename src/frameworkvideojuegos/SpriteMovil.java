

package frameworkvideojuegos;

/**
 *
 * @author Enrique Sánchez 
 */
public abstract class SpriteMovil extends Sprite{
    
    //velocidad mínima 
    private int step = 5;
    
    //factor de rozamiento
    private int rozamientoStep = 1; 
    
    private boolean rozamiento = false;
    
    private boolean colisionesEscena = false;

    protected int vX, vY;

    public SpriteMovil() {
        
    }
    
    protected boolean colisionTop(){
        //si se ha pasado del limite top y la velocidad en y apunta hacia arriba
        return (
                super.getLimTop() < 0
                &&
                vY < 0
                );
    }
    
    protected boolean colisionBottom(){
        //si se ha pasado del limite bottom y la velocidad en y apunta hacia abajo
        return (
                super.getLimBottom() > Sprite.controlador.getHeight()
                &&
                vY > 0
                );
    }
    
    protected boolean colisionLeft(){
        //si se ha pasado del limite left y la velocidad en x apunta a esa dirección
        return (
                super.getLimLeft() < 0
                &&
                vX < 0
                );
    }
    
    protected boolean colisionRight(){
        //si se ha pasado del limite right y la velocidad aen x apunta en esa dirección
        return (
                super.getLimRight() > Sprite.controlador.getWidth()
                &&
                vX > 0
                );
    }
    
    protected void comprobarRebotesEscenario(){
        
        if(colisionTop()){
            vY = 0; 
            y = 0;
        }
        
        if(colisionBottom()){
            vY = 0;
            y = Sprite.controlador.getHeight() - super.height;
        }
        

        if(colisionLeft()){
            vX = 0;
            x = 0;
        }
        
        if(colisionRight()){
            vX = 0;
            x = Sprite.controlador.getWidth() - super.width;
        }
        
        
        
    }
    
    private void aplicarRozamiento(){
        
        //actuación del rozamiento
        if(vX > 0){
            vX -= rozamientoStep;
        }else if(vX < 0){
            vX += rozamientoStep;
        }
        
        if(vY > 0){
            vY -= rozamientoStep;
        }else if(vY < 0){
            vY += rozamientoStep;
        }
        
    }

    @Override
    public void actualizar(){
        
        if(rozamiento){
            aplicarRozamiento();
        }
        
        
        if(colisionesEscena){
            comprobarRebotesEscenario();
        }
              
        this.setX(x += vX);
        this.setY(y += vY);
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getRozamientoStep() {
        return rozamientoStep;
    }

    public void setRozamientoStep(int rozamientoStep) {
        this.rozamientoStep = rozamientoStep;
    }

    public boolean isRozamiento() {
        return rozamiento;
    }

    public void setRozamiento(boolean rozamiento) {
        this.rozamiento = rozamiento;
    }

    public boolean isColisionesEscena() {
        return colisionesEscena;
    }

    public void setColisionesEscena(boolean colisionesEscena) {
        this.colisionesEscena = colisionesEscena;
    }

    public int getvX() {
        return vX;
    }

    public void setvX(int vX) {
        this.vX = vX;
    }

    public int getvY() {
        return vY;
    }

    public void setvY(int vY) {
        this.vY = vY;
    }

    
    

}
