/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

/**
 *
 * @author elixandre
 */
public class Responsables {
    private int count;
    private short responsable;

    public Responsables(short responsable) {
        this.count = 1;
        this.responsable = responsable;
    }    
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public short getResponsable() {
        return responsable;
    }

    public void setResponsable(short responsable) {
        this.responsable = responsable;
    }
    
    public int incrementCount() {
        this.count++;
        return this.count;
    }
}
