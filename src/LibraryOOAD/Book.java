/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

/**
 * A Book type media.
 *
 * @author Spellabbet
 */
public class Book extends Media {
    private String type;
    private String author;
    private String published;
    private String result;
    
    /**
     * Creates a book object.
     *
     * @param id Unique for this instance.
     * @param author Author of this book.
     * @param name Name of this book.
     * @param published Publication date of this book.
     */
    public Book(String id, String author, String name, String published){
        super(id);
        this.type = getClass().getName();
        this.author = author;
        this.name = name;
        this.published = published;
    }

    @Override
    public String toString(){
        result = "Type: " + this.type + "\nID: " + this.getId() + "\nName: " + this.name + "\nAuthor: " + this.author + "\nPublished: " + this.published + "\nAvailable: " + super.available;
        return result;
    }
}
