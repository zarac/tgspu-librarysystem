/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import aVLTree.AVLTree;

/**
 * The catalog also known as the huge fucking awesome controller of the whole world. FUCK EYAH!
 *
 * @author Spellabbet
 */
public class Catalog {

    protected AVLTree<User> users;
    protected User curUser;
    // TODO : index array, eek so much duplicate code!
    protected AVLTree<Media> byID;
    protected AVLTree<Media> byName;
    protected AVLTree<Media> byWild;
    // Used to split title into parts for wild index
    protected String nameRegex = " ";

    /**
     * Creates a new catalog instance.
     */
    public Catalog(){
        this.curUser = null;
        users = new AVLTree<User>();
        byID = new AVLTree<Media>();
        byName = new AVLTree<Media>();
        byWild = new AVLTree<Media>();
        importUsers();
        importMedia();

        this.users.add("admin", new User("admin", "admin", "none"));
    }

    /**
     * Does an exact search.
     *
     * TODO : search(AVLTree index, String key) to avoid duplicate code.
     * 
     * @param key What to search for.
     * @return A string representation of the result.
     */
    public String search(String key){
        String result = "You've searched for '" + key + "'.";

        // by ID
        LinkedList<Media> mediaByID = byID.find(key);
        if (mediaByID != null)
        {
            Iterator<Media> iterator = mediaByID.iterator();
            while (iterator.hasNext())
            {
                Media media = iterator.next();
                result += "\n\n* Found by ID *\n" + media.toString();
            }
        }

        // by Name
        LinkedList<Media> mediaByName = byName.find(key);
        if (mediaByName != null)
        {
            Iterator<Media> iterator = mediaByName.iterator();
            while (iterator.hasNext())
            {
                Media media = iterator.next();
                result += "\n\n* Found by Name *\n" + media.toString();
            }
        }

        // by Wild
        LinkedList<Media> mediaByWild = byWild.find(key.toLowerCase());
        if (mediaByWild != null)
        {
            Iterator<Media> iterator = mediaByWild.iterator();
            while (iterator.hasNext())
            {
                Media media = iterator.next();
                result += "\n\n* Found by Wild *\n" + media.toString();
            }
        }

        return result;
    }
    /**
     * Imports all users from a filé.
     *
     */
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
                this.users.add(parts[0], new User(parts[0], parts[1], parts[2]));
                person = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }
    
    /**
     * Imports all media from a filé.
     *
     */
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

    /**
     * Adds the given media to 3 different AVLtrees.
     *
     * @param media The media to be added.
     */
    public void addMedia(Media media){
        System.out.println("addMedia(" + media.toString() + ")");
        // by ID
        byID.add(media.getId(), media);

        // by Name
        byName.add(media.getName(), media);

        // by Wild (name)
        String[] names = media.getName().split(nameRegex);
        for ( int i = 0; i < names.length; ++i ) {
            byWild.add(names[i].toLowerCase(), media);
        }
    }

    /**
     * Login of given user to the system.
     *
     * @param name Name to check.
     * @param persNr Personal security number to check.
     * @return If login was a success or not.
     */
    public boolean logIn(String name, String persNr) {
        LinkedList<User> temp = users.find(name);
        if(temp.size() > 0){
            if( temp.getFirst().getPersNr().equals(persNr)){
                curUser = temp.getFirst();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns media.
     *
     * @param media The media to be returned.
     * @return If the return was a success or not.
     */
    public boolean returnMedia(Media media){
        if(media.getUser().equals(curUser)){
            media.removeLoanfromUser();
            media.setUser(null);
            media.setAvailability(true);
            return true;
        }
        return false;
    }

    /**
     * Returns media.
     * 
     * @param id The id to find the media.
     * @return If the return was a success or not.
    */
    public boolean returnMedia(String id){
        boolean result = false;
        LinkedList<Media> medias = byID.find(id);
        if(medias.size() == 0){
            return false;
        } else {
            Media media = medias.getFirst();
            if(media != null && !media.available){
                result = returnMedia(media);
            }
        }
        return result;
    }

    /**
     * Loans a media.
     *
     * @param media The media to be loaned.
     */
    public void loanMedia(Media media){
        media.setUser(curUser);
        media.setAvailability(false);
        curUser.addLoan(media);
    }

    
    /**
     * Loans a media.
     *
     * @param id The id of a media to be loaned.
     * @return If the loan was a success or not.
     */
    public boolean loanMedia(String id){
        LinkedList<Media> medias = byID.find(id);
        if(medias.size() == 0){
            return false;
        } else {
            Media media = medias.getFirst();
            if(media != null && media.available){
                loanMedia(media);
                return true;
            }
        }
        return false;
    }
    /**
     * Gets the current user logged in.
     *
     * @return The current user logged in.
     */
    public User getCurrentUser(){
        return this.curUser;
    }

    /**
     * Starts the system.
     *
     * @param args The argument.
     */
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        GUI gui = new GUI(catalog);
        }
}
