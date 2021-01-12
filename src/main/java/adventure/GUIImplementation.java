package adventure;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

public class GUIImplementation extends JFrame {
    private Game game;
    private Player player;

    private String savecommand;
    private JTextArea inventory = new JTextArea();

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    private Container contentPane;
    private TextField modelText;

    public GUIImplementation(Game theGame) {
        super();
        game = theGame;
        setUpSize();
        setMainContainer();
    }

    private void setUpSize() {
        setSize(WIDTH, HEIGHT);
        setTitle("The A3 Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //the next few methods set up the three panels used for the demo GUI
    private void setMainContainer() {
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JLabel theLabel = new JLabel("Welcome Player!");
        contentPane.add(theLabel, BorderLayout.PAGE_START);
        contentPane.add(firstPanel(theLabel, game), BorderLayout.CENTER);
    }

    /* this panel just holds the other panels.  We could have added
    them directly to the main container but I wanted to show you how you can nest containers*/
    private JPanel firstPanel(JLabel theLabel, Game aGame) {
        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.PAGE_AXIS));
        thePanel.add(addButtons(theLabel, aGame));
        thePanel.add(showInventory(player, inventory));
        thePanel.add(gameOutput());
        thePanel.add(acceptAction(aGame));
        thePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        return thePanel;
    }

    /* this panel contains a listener that interacts with my "game"
    class.  The Game class is also known as the 'model' in OO design */
    private JPanel addButtons(JLabel theLabel, Game aGame) {
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton changeName = new JButton("Change your name");
        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("What should your new name be?");
                if(name!=null && !name.isEmpty()) {
                    theLabel.setText("Welcome " + name);
                }
            }
        });
        JButton loadJSON = new JButton("Load JSON File");
        loadJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jsonpath = JOptionPane.showInputDialog("What is the path to your JSON File?");
                if(jsonpath!=null && !jsonpath.isEmpty()) {
                    String[] path = new String[]{jsonpath};
                    try {
                        Runtime.getRuntime().exec("java -jar 2430_A2-1.0-jar-with-dependencies.jar", path);
                    } catch (IOException ex) {
                     ex.printStackTrace();
                     JOptionPane.showMessageDialog(null,"Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(inventory, "Your JSON File was successfully loaded");
                }
            }
        });
        JButton loadSavedGame = new JButton("Load saved game");
        loadSavedGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String loadfilename = JOptionPane.showInputDialog("What is the path to your savefile?");
                if(loadfilename!=null && !loadfilename.isEmpty()) {
                    try {
                        aGame.loadGame(loadfilename + ".ser");
                        JOptionPane.showMessageDialog(inventory, "Your savefile has been loaded successfully");
                    }catch (InvalidCommandException ex){
                        JOptionPane.showMessageDialog(null,"Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JButton saveGame = new JButton("Save game");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String savefilename = JOptionPane.showInputDialog("What should your save file be called?");
                if(savefilename!=null && !savefilename.isEmpty()) {
                    try {
                        aGame.saveGame(Game.getAdventure(), savefilename + ".ser");
                    }catch (InvalidCommandException ex){
                        JOptionPane.showMessageDialog(null,"Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(inventory, "Your game has been saved");
                }
            }
        });
        JButton quitgame = new JButton("Quit");
        quitgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String savedecision = JOptionPane.showInputDialog("Would you like to save your game progress? (Y/N)");
                if(savedecision!=null && !savedecision.isEmpty()) {
                    if(savedecision.equalsIgnoreCase("Y")||savedecision.equalsIgnoreCase("N")) {
                        if (savedecision.equalsIgnoreCase("Y")) {
                            String savefilename = JOptionPane.showInputDialog("What should your file be called? "
                                    + "(without the ser ending)");
                            if (savefilename != null && !savefilename.isEmpty()) {
                                try {
                                    aGame.saveGame(Game.getAdventure(), savefilename + ".ser");
                                } catch (InvalidCommandException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid Input", 
                                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                }
                                System.out.println("Your progress has been saved. Goodbye!");
                            }
                        } else {
                            System.out.println("\nYour game progress has not been saved. Goodbye!");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(changeName);
        buttonPanel.add(loadJSON);
        buttonPanel.add(loadSavedGame);
        buttonPanel.add(saveGame);
        buttonPanel.add(quitgame);
        return buttonPanel;
    }

    private JPanel showInventory(Player aPlayer, JTextArea aInventory) {
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Inventory: ");
        inventoryPanel.add(label);
        aInventory.append("[]");
        inventoryPanel.add(aInventory);
        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return inventoryPanel;
    }

    private JPanel gameOutput() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        JTextArea outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputPanel.add(scrollPane);
        PrintStream printStream = new PrintStream(new CustomOutputStream(outputArea));
        System.setOut(printStream);
        System.setErr(printStream);

        return outputPanel;
    }

    private JPanel acceptAction(Game aGame) {
        int collumns = 14+1;
        int width = 249+1;
        int height = 24+1;
        JPanel acceptPanel = new JPanel();
        acceptPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Your action: ");
        acceptPanel.add(label);
        JTextField acceptAction = new JTextField();
        acceptAction.setColumns(collumns);
        acceptAction.setPreferredSize(new Dimension(width, height));
        acceptPanel.add(acceptAction);
        JButton gobutton = new JButton("Go!");
        gobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = acceptAction.getText();
                acceptAction.setText("");
                try {
                    aGame.doInstruction(aGame, player, command);
                    updateInventory(player);
                } catch (InvalidCommandException ex) {
                    ex.printStackTrace();
                }
            }

            private void updateInventory(Player aPlayer) {
                    inventory.setText(aPlayer.readInventory());
                }
        });
        acceptPanel.add(gobutton);
        return acceptPanel;
    }

    /** Method return Object Game
     * @return Game object
     */
    public Game getGame() {
        return game; }

    /** Method set Object Game
     * @param aGame will be set as Game
     */
    public void setGame(Game aGame) {
        this.game = aGame;
    }

    /** Method return Object Player
     * @return Player object
     */
    public Player getPlayer() {
        return player;
    }

    /** Method set Object Player
     * @param aPlayer will be set as Player
     */
    public void setPlayer(Player aPlayer) {
        this.player = aPlayer;
    }
}
