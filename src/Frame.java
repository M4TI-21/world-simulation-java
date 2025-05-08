import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Frame extends JFrame {
    private JTextArea logArea;
    private World world;
    private GameBoard gameBoard;
    private Human human;
    public Frame() {
        setTitle("World Simulation - Mateusz Hann 203308");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //enable exit button
        setResizable(false);  //disable window resizing
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);    //window dimensions
        setLayout(new BorderLayout());     //divide frame into parts

        //scrollable message console
        logArea = new JTextArea();
        logArea.setEditable(false);     //disable editing console content
        logArea.setBackground(Color.black);
        logArea.setForeground(Color.white);

        JScrollPane logConsole = new JScrollPane(logArea);
        logConsole.setPreferredSize(new Dimension(Constants.CONSOLE_WIDTH, Constants.BOARD_HEIGHT));
        add(logConsole, BorderLayout.EAST);

        gameBoard = new GameBoard();
        add(gameBoard, BorderLayout.CENTER);
        gameBoard.setFocusable(true);
        gameBoard.requestFocusInWindow();

        world = new World(logArea, gameBoard);
        human = world.getHuman();

        InputMap inputMap = gameBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gameBoard.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        actionMap.put("moveUp", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_UP);
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_DOWN);
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_LEFT);
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_RIGHT);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "nextTurn");
        actionMap.put("nextTurn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.hasHumanMoved()) {
                    world.makeTurn();
                    world.setHumanMoved(false);
                }
            }
        });

        setFocusable(true);     //allow key press listening
        setVisible(true);
    }

    public World getWorld() {
        return world;
    }
}

class GameBoard extends JPanel {
    private List<Organism> organisms;

    public GameBoard() {
        this.setBackground(new Color(100, 200, 100));  //background color
        this.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
    }

    public void setOrganisms(List<Organism> organisms) {
        this.organisms = organisms;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(10, 100, 10));
        g2d.setStroke(new BasicStroke(Constants.FIELD_SIZE));
        g2d.drawRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        if (organisms != null) {
            for (Organism organism : organisms) {
                organism.draw(g2d);
            }
        }
    }

}