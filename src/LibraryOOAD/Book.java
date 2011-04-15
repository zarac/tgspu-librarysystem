/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

/**
 *
 * @author Spellabbet
 */
public class Book extends Media {
    private String type;
    private String author;
    private String name;
    private String published;
    private String result;
    
    public Book(String id, String author, String name, String published){
        super(id);
        this.type = getClass().getName();
        this.author = author;
        this.name = name;
        this.published = published;
    }

    public String toString(){
        result = "Type: " + this.type + "\nID: " + this.getId() + "\nName: " + this.name + "\nAuthor: " + this.author + "\nPublished: " + this.published;
        return result;
    }
}
