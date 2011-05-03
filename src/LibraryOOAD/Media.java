/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;


/**
 * General Media object.
 * 
 * @author Spellabbet
 */
public abstract class Media {
    protected String id;
    protected boolean available;
    protected User loaner;
    protected String name;

    /**
     * Creates new instance of Media.
     *
     * @param id Unique for this Media object.
     */
    public Media( String id ) {
        this.id = id;
        this.available = true;
        this.loaner = null;
        name = "unnamed";
    }

    /**
     * Returns ID of Media object.
     *
     * @return The ID.
     */
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

    /**
     * Gets the availability of this instance.
     * 
     * @return Available or not.
     */
    public boolean getAvailability(){
        return this.available;
    }

    /**
     * Gets the the current loaner of this instance.
     *
     * @return The loaner.
     */
    public User getUser(){
        return this.loaner;
    }

    /**
     * Sets the loaner of this instance.
     *
     * @param user The loaner.
     */
    public void setUser(User user){
        this.loaner = user;
    }

    /**
     * Sets the availbility of this instance.
     *
     * @param avail The availability.
     */
    public void setAvailability(boolean avail){
        if(avail){
            this.available = true;
        }else{
            this.available = false;
        }
    }

    /**
     * {@inheritDoc}
     * @see Object#equals()
     */
    @Override public boolean equals( Object obj ) {
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
}
