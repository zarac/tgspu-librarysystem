/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;


/**
 *
 * @author Spellabbet
 */
public abstract class Media {
    private String id;
    private boolean available;
    private User loaner;

    public Media( String id ) {
        this.id = id;
        this.available = true;
        this.loaner = null;
    }
    public String getId() {
        return id;
    }
    public boolean getAvailability(){
        return this.available;
    }
    public User getUser(){
        return this.loaner;
    }
    public void setUser(User user){
        this.loaner = user;
    }
    public void setAvailability(boolean avail){
        if(avail){
            this.available = true;
        }else{
            this.available = false;
        }
    }
    public boolean equals( Object obj ) {
        Media media = (Media)obj;
        return id.equals( media.getId() );
    }
    public void removeLoanfromUser(){
        loaner.removeLoan(this);
    }
}