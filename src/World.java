import javax.swing.*;
import java.util.*;
import java.awt.KeyboardFocusManager;
import java.awt.event.*;

public class World {
    private JTextArea logArea;
    private GameBoard gameBoard;
    private List<Organism> organisms;
    public static List<String> logs = new ArrayList<>();
    private static int turnNumber = 1;

    public World(JTextArea logArea, GameBoard gameBoard) {
        this.logArea = logArea;
        this.gameBoard = gameBoard;
        this.organisms = new ArrayList<>();
        addLog("World has been created.");

    }

    //add logs to log list
    public void addLog(String log) {
        logs.add(log);
        updateLog();
    }

    //refresh log console
    private void updateLog() {
        if (logArea != null) {
            logArea.setText("");
            for (String log : logs) {
                logArea.append(log + "\n");
            }
        }
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void addOrganism(String type) {
        Organism newOrganism = null;
        int[] position = selectPosition();
        int x = position[0];
        int y = position[1];
        switch(type) {
            case "Grass":
                newOrganism = new Grass(0, 0, 0, x, y, this);
                break;
            case "Sow thistle":
                newOrganism = new SowThistle(0, 0, 0, x, y, this);
                break;
            case "Guarana":
                newOrganism = new Guarana(0, 0, 0, x, y, this);
                break;
            case "Belladonna":
                newOrganism = new Belladonna(0, 0, 0, x, y, this);
                break;
            case "Hogweed":
                newOrganism = new Hogweed(0, 0, 0, x, y, this);
                break;
            case "Sheep":
                newOrganism = new Sheep(0, 0, 0, x, y, this);
                break;
            case "Wolf":
                newOrganism = new Wolf(0, 0, 0, x, y, this);
                break;
            case "Fox":
                newOrganism = new Fox(0, 0, 0, x, y, this);
                break;
            case "Turtle":
                newOrganism = new Turtle(0, 0, 0, x, y, this);
                break;
            case "Antelope":
                newOrganism = new Antelope(0, 0, 0, x, y, this);
                break;
        }

        if (newOrganism != null) {
            organisms.add(newOrganism);
            drawWorld();
        }
    }

    public int[] selectPosition() {
        Random random = new Random();
        int x = 0, y = 0;
        boolean isFree = false;

        while (!isFree) {
            x = random.nextInt(Constants.BOARD_WIDTH / Constants.FIELD_SIZE - 2) + 1;
            y = random.nextInt(Constants.BOARD_HEIGHT / Constants.FIELD_SIZE - 2) + 1;

            x *= Constants.FIELD_SIZE;
            y *= Constants.FIELD_SIZE;

            isFree = isPositionFree(x, y);
        }

        return new int[]{x, y};
    }

    public boolean isPositionFree(int x, int y) {
        for (Organism organism : getOrganisms()) {
            if (organism.getX() == x && organism.getY() == y) {
                return false;
            }
        }
        return true;
    }

    //refresh board
    public void drawWorld() {
        gameBoard.setOrganisms(organisms);
        SwingUtilities.invokeLater(() -> gameBoard.repaint());
    }

    public void makeTurn() {
        addLog("----- Turn " + turnNumber + " has started -----");

        List<Organism> filteredOrganisms = new ArrayList<>();
        for (Organism organism : organisms) {
            filteredOrganisms.add(organism);
        }

        for (Organism organism : filteredOrganisms) {

            organism.action();
            organism.increaseAge();
        }
        addLog("----- Turn " + turnNumber + " has ended -----");
        turnNumber++;
        drawWorld();
    }

    public void pushOrganism(Organism organism) {
        organisms.add(organism);
    }
}
