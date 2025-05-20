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
        gameBoard.setWorld(world);
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

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "activateAbility");
        actionMap.put("activateAbility", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_A);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "saveGame");
        actionMap.put("saveGame", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_S);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "loadGame");
        actionMap.put("loadGame", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                world.handleKeyPress(KeyEvent.VK_L);
            }
        });

        String[] organism_types = {
                "Hogweed", "Wolf", "Sheep", "Fox", "Turtle", "Antelope",
                "Grass", "Sow thistle", "Guarana", "Belladonna"
        };

        //iterative key binding for organism selection
        for (int i = 0; i <= 9; i++) {
            final int index = i;
            inputMap.put(KeyStroke.getKeyStroke(String.valueOf(i)), "select" + i);
            actionMap.put("select" + i, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index < organism_types.length) {
                        world.selectOrganism(organism_types[index]);
                    }
                }
            });
        }

        setFocusable(true);     //allow key press listening
        setVisible(true);
    }

    public World getWorld() {
        return world;
    }
}

class GameBoard extends JPanel {
    private List<Organism> organisms;
    private World world;

    public GameBoard() {
        this.setBackground(new Color(100, 200, 100));  //background color
        this.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
    }

    public void setWorld(World world) {
        this.world = world;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / Constants.FIELD_SIZE;
                int y = e.getY() / Constants.FIELD_SIZE;
                world.addOrganismOnClick(x, y);
            }
        });
    }

    public void setOrganisms(List<Organism> organisms) {
        this.organisms = organisms;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //drawing grid
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        int cols = Constants.BOARD_WIDTH / Constants.FIELD_SIZE;
        int rows = Constants.BOARD_HEIGHT / Constants.FIELD_SIZE;
        for (int i = 0; i <= cols; i++) {
            int x = i * Constants.FIELD_SIZE;
            g2d.drawLine(x, 0, x, Constants.BOARD_HEIGHT);
        }
        for (int j = 0; j <= rows; j++) {
            int y = j * Constants.FIELD_SIZE;
            g2d.drawLine(0, y, Constants.BOARD_WIDTH, y);
        }

        //drawing organisms
        if (organisms != null) {
            for (Organism organism : world.getOrganismsList()) {
                organism.draw(g2d);
            }
        }
    }


}