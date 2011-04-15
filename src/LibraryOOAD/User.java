/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

import java.util.*;

/**
 *
 * @author Spellabbet
 */
public class User {
    private String persNr;
    private String name;
    private String tele;
    private ArrayList<Media> loansArray;

    public User(String nr, String name, String tele){
        this.persNr = nr;
        this.name = name;
        this.tele = tele;
        this.loansArray = new ArrayList<Media>();
    }
    public String getPersNr(){
        return this.persNr;
    }
    public String getName(){
        return this.name;
    }
     public String getTele(){
        return this.tele;
    }
    public void removeLoan(Media media){
        loansArray.remove(media);
    }
    public void addLoan(Media media){
        loansArray.add(media);
    }
}
