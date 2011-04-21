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
    protected String id;
    protected boolean available;
    protected User loaner;
    protected String name;

    public Media( String id ) {
        this.id = id;
        this.available = true;
        this.loaner = null;
        name = "unnamed";
    }

    public String getId() {
        return id;
    }

    /**
     * Gets the name for this instance.
     *
     * @return The name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name for this instance.
     *
     * @param name The name.
     */
    public void setName(String name)
    {
        this.name = name;
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

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return "ID='" + id + "', name='" + name + "'";
    }

    public void removeLoanfromUser(){
        loaner.removeLoan(this);
    }
}
