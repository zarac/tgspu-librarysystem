/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;


/**
 * A DvD type media.
 *
 * @author Spellabbet
 */
public class DVD extends Media {
    private String type;
    private String published;
    private String[] actors;
    private String result;
    private String actorsString;
    
    /**
     * Creates a DVD object.
     *
     * @param id Unique for this instance
     * @param name Title of this media.
     * @param published Date of publication.
     * @param actors Actors associated with media.
     */
    public DVD(String id, String name, String published, String[] actors){
        super(id);
        this.type = getClass().getName();
        this.name = name;
        this.published = published;
        this.actors = actors;
    }

    protected String actorsToString(){
        actorsString = "";
        for(int i = 0; i<actors.length ; i++){
            actorsString = actorsString + actors[i] + ", ";
        }
        return actorsString;
    }

    @Override
    public String toString(){
        result = "Type: " + this.type + "\nID: " + this.getId() + "\nName: " + this.name + "\nActors: " + actorsToString() + "\nPublished: " + this.published + "\nAvailable: " + super.available;
        return result;
    }
}
