/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibraryOOAD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

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
    private JTextField name = new JTextField("admin");
    private JTextField persNr = new JTextField("admin");
    //private JTextField name = new JTextField("Your name");
    //private JTextField persNr = new JTextField("Your Social Security Number");
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
    protected WelcomePanel panelWelcome;

    public GUI(Catalog catalog){
        this.catalog = catalog;

        panelWelcome = new WelcomePanel();

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

        // default view
        setView(panelWelcome);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bLogIn) {
            if (catalog.logIn(name.getText(), persNr.getText())) {
                this.setVisible(true);
                fLogIn.setVisible(false);
            } else if (name.getText().equals("din mamma")) {
                lLogIn.setText("ERROR! NUKE HAS BEEN SENT TO YOUR MOMS LOCATION!");
            } else {
                lLogIn.setText("ERROR! NUKE SENT TO YOUR LOCATION!");
            }
        } else if (e.getSource() == search) {
            setView(panelSearch);
        } else if (e.getSource() == viewLoans) {
            setView(null);
        } else if (e.getSource() == newLoan) {
            setView(null);
        } else if (e.getSource() == returnMedia) {
            setView(null);
        } else if (e.getSource() == logOut) {
            catalog.curUser = null;
            this.setVisible(false);
            fLogIn.setVisible(true);
        } else if (e.getSource() == bSearch) {
            doSearch();
        }
    }

    public void setView(JPanel panel){
        view.removeAll();

        if (panel == null)
            view.add(panelWelcome);
        else
            view.add(panel);

        view.revalidate();
        view.repaint();
    }

    public void doSearch(){
        searchResultPanel.setText(catalog.search(searchInput.getText()));
        scroll.revalidate();
    }

    protected class WelcomePanel extends JPanel implements ActionListener
    {
        protected int width, height;
        protected Graphics g;
        protected Font font;
        protected int currentColor, initialColor;
        protected Color[] colors;
        protected Timer matrixEffect;

        protected WelcomePanel()
        {
            setBackground(Color.BLACK);
            setForeground(Color.GREEN);
            font = new Font("monospaced", Font.PLAIN, 12);

            // some nice green colors
            colors = new Color[]
            {
                new Color(50, 255, 50),
                new Color(60, 255, 60),
                new Color(70, 255, 70),
                new Color(80, 255, 80),
                new Color(90, 255, 90),
                new Color(100, 255, 100),
                new Color(110, 255, 110),
                new Color(120, 255, 120),
                new Color(130, 255, 130),
                new Color(140, 255, 140),
                new Color(150, 255, 150),
                new Color(160, 255, 160),
                new Color(170, 255, 170),
                new Color(180, 255, 180),
                new Color(190, 255, 190),
                new Color(200, 255, 200),
                new Color(210, 255, 210),
            };

            matrixEffect = new Timer(1000/18, this);

            // TODO : ? Start and stop when appropriate.
            matrixEffect.start();
        }

        /**
         * {@inheritDoc}
         * @see JComponent#paintComponent(Graphics)
         */
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            this.g = g;
            width = getWidth();
            height = getHeight();

            nextColor();
            drawWelcomeText();
        }

        protected Color nextColor()
        {
            if (++currentColor >= colors.length)
                currentColor = 0;
            return colors[currentColor];
        }

        protected Color nextInitialColor()
        {
            if (--initialColor < 0)
                initialColor=colors.length-1;
            currentColor = initialColor;
            return colors[currentColor];
        }

        protected void drawWelcomeText()
        {
            g.setFont(font);

            g.setColor(nextInitialColor());
            g.drawString("Welcome", 20, 20);
            g.setColor(nextColor());
            g.drawString(" elcome", 20, 30);
            g.setColor(nextColor());
            g.drawString("  lcome", 20, 40);
            g.setColor(nextColor());
            g.drawString("   come", 20, 50);
            g.setColor(nextColor());
            g.drawString("    ome", 20, 60);
            g.setColor(nextColor());
            g.drawString("     me", 20, 70);
            g.setColor(nextColor());
            g.drawString("      e", 20, 80);

            g.setColor(nextColor());
            g.drawString("* Awesome Terminator Matrix Library System *", 20, 190);
            g.setColor(nextColor());
            g.drawString("Created by:", 20, 205);
            g.setColor(nextColor());
            g.drawString(" - Daniel Aladics", 20, 220);
            g.setColor(nextColor());
            g.drawString(" - Hannes Landstedt", 20, 230);
            g.setColor(nextColor());
            g.drawString(" - Jens Persson", 20, 250);
            g.setColor(nextColor());
            g.drawString(" - Magnus Grönvall", 20, 240);
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            repaint();
        }
    }
}
