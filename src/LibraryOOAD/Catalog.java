/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

import aVLTree.AVLTree;

/**
 *
 * @author Spellabbet
 */
public class Catalog {

    protected ArrayList<User> userArrayList = new ArrayList<User>();
    protected User curUser;
    protected AVLTree<Media> byID;
    protected AVLTree<Media> byName;

    public Catalog(){
        this.curUser = null;
        this.userArrayList.add(new User("admin", "admin", "none"));
        byID = new AVLTree<Media>();
        byName = new AVLTree<Media>();
        importUsers();
        importMedia();
    }

    /**
     * Does an exact search.
     * 
     * @param key What to search for.
     * @return A string representation of the result.
     */
    public String search(String key){
        String result = "You've searched for '" + key + "'.";

        // by ID
        Media mediaByID = byID.find(key);
        if (mediaByID != null)
            result += "\n\nFound by ID: " + mediaByID.toString();

        // by Name
        Media mediaByName = byName.find(key);
        if (mediaByName != null)
            result += "\n\nFound by Name: " + mediaByName.toString();

        return result;
    }
    
    public void importUsers(){
        String person;
        String[] parts;
        System.out.println("CWD: " + System.getProperty("user.dir"));
        String path = "data/Lantagare.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
//            Example:
//            891216-1111;Harald Svensson;040-471024
            person = br.readLine();
            while(person != null){
                parts = person.split(";");
                this.userArrayList.add(new User(parts[0], parts[1], parts[2]));
                person = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void importMedia(){
        String media;
        String[] parts;
        System.out.println("CWD: " + System.getProperty("user.dir"));
        String path = "data/Media.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
//            Examples:
//            Bok;427769;Deitel;Java how to program;2005
//            Dvd;635492;Nile City 105,6;1994;Robert Gustavsson;Johan Rheborg;Henrik Schyffert
            media = br.readLine();
            while(media != null){
                parts = media.split(";");
                if(parts[0].equals("Bok")){
                    Book book = new Book(parts[1], parts[2], parts[3], parts[4]);
                    addMedia(book);
                }else{
                    String[] actors = Arrays.copyOfRange(parts, 4, parts.length);
                    DVD dvd = new DVD(parts[1], parts[2], parts[3], actors);
                    addMedia(dvd);
                }
                media = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void addMedia(Media media){
        System.out.println("addMedia(" + media.toString() + ")");
        byID.add(media.getId(), media);
        byName.add(media.getName(), media);
    }

    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        GUI gui = new GUI(catalog);
    }

    public boolean logIn(String name, String persNr) {
        for(int i = 0 ; i < userArrayList.size();i++) {
            if((name.equals(userArrayList.get(i).getName())) && persNr.equals(userArrayList.get(i).getPersNr())){
                curUser = userArrayList.get(i);
                return true;
            }
        }
        return false;
    }
}
