/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

import java.util.*;

/**
 * A User.
 * 
 * @author Spellabbet
 */
public class User {
    private String persNr;
    private String name;
    private String tele;
    private ArrayList<Media> loansArray;

    /**
     * Creates a new instance of User.
     * 
     * @param nr Personal security number of this user.
     * @param name Name of this user.
     * @param tele Telephone number to this user.
     */
    public User(String nr, String name, String tele){
        this.persNr = nr;
        this.name = name;
        this.tele = tele;
        this.loansArray = new ArrayList<Media>();
    }

    /**
     * Gets this user's personal security number.
     *
     * @return The personal security number.
     */
    public String getPersNr(){
        return this.persNr;
    }
    /**
     * Gets this user's name.
     *
     * @return The name.
     */
    public String getName(){
        return this.name;
    }
    /**
     * Gets this user's telephonenumber.
     *
     * @return The telephone number.
     */
    public String getTele(){
        return this.tele;
    }
     /**
      * Removes a media from the current loans of this instance.
      *
      * @param media The media to be removed from the users loans.
      */
     public void removeLoan(Media media){
        loansArray.remove(media);
    }
    /**
     * Adds a media to the current loans of this instance.
     *
     * @param media The media to be added to the users loans.
     */
    public void addLoan(Media media){
        loansArray.add(media);
    }
    /**
     * Gets all of this user's loans.
     *
     * @return All of the loans.
     */
    public ArrayList<Media> getLoans(){
        return loansArray;
    }
}
