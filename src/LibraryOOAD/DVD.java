/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

/**
 *
 * @author Spellabbet
 */
public class DVD extends Media {
    private String type;
    private String published;
    private String[] actors;
    private String result;
    private String actorsString;
    
    public DVD(String id, String name, String published, String[] actors){
        super(id);
        this.type = getClass().getName();
        this.name = name;
        this.published = published;
        this.actors = actors;
    }

    public String actorsToString(){
        for(int i = 0; i<actors.length ; i++){
            actorsString = actorsString + actors[i] + ", ";
        }
        return actorsString;
    }
    public String toString(){
        result = "Type: " + this.type + "\nID: " + this.getId() + "\nName: " + this.name + "\nActors: " + actorsToString() + "\nPublished: " + this.published;
        return result;
    }
}
