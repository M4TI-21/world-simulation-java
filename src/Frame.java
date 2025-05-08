import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Frame extends JFrame {
    private JTextArea logArea;
    private World world;
    private GameBoard gameBoard;

    public Frame() {
        this.setTitle("World Simulation - Mateusz Hann 203308");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //enable exit button
        //this.setLocationRelativeTo(null);   //window in the middle
        this.setResizable(false);  //disable window resizing
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);    //window dimensions
        this.setLayout(new BorderLayout());     //divide frame into parts

        //scrollable message console
        logArea = new JTextArea();
        logArea.setEditable(false);     //disable editing console content
        logArea.setBackground(Color.black);
        logArea.setForeground(Color.white);

        JScrollPane logConsole = new JScrollPane(logArea);
        logConsole.setPreferredSize(new Dimension(Constants.CONSOLE_WIDTH, Constants.BOARD_HEIGHT));
        this.add(logConsole, BorderLayout.EAST);

        gameBoard = new GameBoard();
        this.add(gameBoard, BorderLayout.CENTER);

        world = new World(logArea, gameBoard);

        InputMap inputMap = gameBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gameBoard.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "nextTurn");
        actionMap.put("nextTurn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.makeTurn();
            }
        });

        this.setVisible(true);
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