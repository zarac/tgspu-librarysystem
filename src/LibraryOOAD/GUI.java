/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Spellabbet
 */
public class GUI extends JFrame implements ActionListener{
    //Log in
    private JPanel pLogIn = new JPanel(new GridLayout(4, 1));
    private JLabel lLogIn = new JLabel("Please log in", (int) CENTER_ALIGNMENT);
    private JFrame fLogIn = new JFrame();
    private JButton bLogIn = new JButton("Log in");
    private JTextField name = new JTextField("Your name");
    private JTextField persNr = new JTextField("Your Social Security Number");
    private Catalog catalog;

    //library window, when logged in
    private JButton search = new JButton("Search");
    private JButton viewLoans = new JButton("View current loans");
    private JButton newLoan = new JButton("New loan");
    private JButton returnMedia = new JButton("Return media");
    private JButton logOut = new JButton("Log out");
    private JPanel knappar = new JPanel(new GridLayout(5,1));
    private JPanel view = new JPanel(new GridLayout(1,1));

    // all different view panels
    private JPanel panelSearch = new JPanel(new BorderLayout());
    private JPanel searchBar = new JPanel();
    private JTextField searchInput = new JTextField("input");
    private JButton bSearch = new JButton("Search");
    private JTextArea searchResultPanel = new JTextArea();
    private JScrollPane scroll = new JScrollPane(searchResultPanel);

    private JPanel panelViewLoans = new JPanel();
    private JPanel panelNewLoan = new JPanel();
    private JPanel panelReturnMedia = new JPanel();

    public GUI(Catalog catalog){
        this.catalog = catalog;

        // settings for login panel data
        fLogIn.setTitle("HackerLibary - Log in - v3.0");
        fLogIn.setBounds(400, 400, 400, 200);
        fLogIn.setResizable(false);
        fLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pLogIn.setBounds(400, 400, 400, 200);
        pLogIn.setBackground(Color.BLACK);
        name.setSize(100, 50);
        name.setBackground(Color.BLACK);
        persNr.setBackground(Color.BLACK);
        name.setForeground(Color.GREEN);
        persNr.setForeground(Color.GREEN);
        lLogIn.setForeground(Color.GREEN);
        bLogIn.setForeground(Color.GREEN);
        bLogIn.setBackground(Color.BLACK);
        pLogIn.add(lLogIn);
        pLogIn.add(name);
        pLogIn.add(persNr);
        pLogIn.add(bLogIn);
        fLogIn.add(pLogIn);
        name.setCaretColor(Color.red);
        persNr.setCaretColor(Color.red);
        bLogIn.addActionListener(this);
        fLogIn.setVisible(true);

        // settings for library view
        this.setBounds(13+37, 13+37, 500, 500);
        this.setTitle("HackerLibary - v3.0");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        knappar.add(search);
        knappar.add(viewLoans);
        knappar.add(newLoan);
        knappar.add(returnMedia);
        knappar.add(logOut);
        view.setForeground(Color.GREEN);
        search.setForeground(Color.GREEN);
        viewLoans.setForeground(Color.GREEN);
        newLoan.setForeground(Color.GREEN);
        returnMedia.setForeground(Color.GREEN);
        logOut.setForeground(Color.GREEN);
        view.setBackground(Color.BLACK);
        search.setBackground(Color.BLACK);
        viewLoans.setBackground(Color.BLACK);
        newLoan.setBackground(Color.BLACK);
        returnMedia.setBackground(Color.BLACK);
        logOut.setBackground(Color.BLACK);

        this.add(BorderLayout.WEST, knappar);
        this.add(BorderLayout.CENTER, view);

        search.addActionListener(this);
        viewLoans.addActionListener(this);
        newLoan.addActionListener(this);
        returnMedia.addActionListener(this);
        logOut.addActionListener(this);

        // Search panel
        searchInput.setPreferredSize(new Dimension(100, 20));
        bSearch.setPreferredSize(new Dimension(100, 20));
        searchInput.setForeground(Color.GREEN);
        searchInput.setBackground(Color.BLACK);
        bSearch.setForeground(Color.GREEN);
        bSearch.setBackground(Color.BLACK);
        searchBar.setBackground(Color.BLACK);
        searchResultPanel.setForeground(Color.GREEN);
        searchResultPanel.setBackground(Color.BLACK);
        searchBar.add(searchInput);
        searchBar.add(bSearch);
        panelSearch.add(BorderLayout.CENTER, scroll);
        panelSearch.add(BorderLayout.NORTH, searchBar);
        bSearch.addActionListener(this);
        // view loans panel
//        panelViewLoans.add(null);
        // new loan panel
//        panelNewLoan.add(null);
        // return media panel
//        panelReturnMedia.add(null);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bLogIn){
            if( catalog.logIn(name.getText(), persNr.getText()) ){
            this.setVisible(true);
            fLogIn.setVisible(false);
        }else if(name.getText().equals("din mamma")){
            lLogIn.setText("ERROR! NUKE HAS BEEN SENT TO YOUR MOMS LOCATION!");
        }else{
            lLogIn.setText("ERROR! NUKE SENT TO YOUR LOCATION!");
            }
        }else if(e.getSource() == search){
            setView(panelSearch);
        }else if(e.getSource() == viewLoans){
            setView(null);
        }else if(e.getSource() == newLoan){
            setView(null);
        }else if(e.getSource() == returnMedia){
            setView(null);
        }else if(e.getSource() == logOut){
            catalog.curUser = null;
            this.setVisible(false);
            fLogIn.setVisible(true);
        }else if(e.getSource() == bSearch){
            doSearch();
        }
    }

    public void setView(JPanel panel){
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.GREEN);
        view.removeAll();
        view.add(panel);
        view.revalidate();
    }

    public void doSearch(){
        searchResultPanel.setText(catalog.search(searchInput.getText()));
        scroll.revalidate();
    }
}
