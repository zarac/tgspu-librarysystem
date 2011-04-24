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
import java.util.Iterator;
import java.util.LinkedList;

import aVLTree.AVLTree;

/**
 *
 * @author Spellabbet
 */
public class Catalog {

    protected ArrayList<User> userArrayList = new ArrayList<User>();
    protected User curUser;
    // TODO : index array, eek so much duplicate code!
    protected AVLTree<Media> byID;
    protected AVLTree<Media> byName;
    protected AVLTree<Media> byWild;
    // Used to split title into parts for wild index
    protected String nameRegex = " ";

    public Catalog(){
        this.curUser = null;
        this.userArrayList.add(new User("admin", "admin", "none"));
        byID = new AVLTree<Media>();
        byName = new AVLTree<Media>();
        byWild = new AVLTree<Media>();
        importUsers();
        importMedia();
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
