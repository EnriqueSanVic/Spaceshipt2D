/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameworkvideojuegos;

/**
 *
 * @author Enrique Sánchez
 */
public interface Actor {
    
    void movLeftAction(boolean act);
    
    void movRightAction(boolean act);
    
    void movTopAction(boolean act);
    
    void movBottomAction(boolean act);
    
    void disparar(boolean act);
    
}
