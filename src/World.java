import javax.swing.*;
import java.util.*;
import java.awt.KeyboardFocusManager;
import java.awt.event.*;

public class World {
    private JTextArea logArea;
    private GameBoard gameBoard;
    private List<Organism> organisms;
    private static int turnNumber = 1;
    private Human human;
    private int keyPressed = 0;
    private boolean humanMoved = false;

    public static List<String> logs = new ArrayList<>();

    public World(JTextArea logArea, GameBoard gameBoard) {
        this.logArea = logArea;
        this.gameBoard = gameBoard;
        this.organisms = new ArrayList<>();
        addLog("World has been created");
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
            case "Human":
                newOrganism = new Human(0, 0, 0, x, y, this);
                this.human = (Human) newOrganism;
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

        //only alive organisms can make turn
        List<Organism> filteredOrganisms = new ArrayList<>();
        for (Organism organism : organisms) {
            if (organism.isAlive){
                filteredOrganisms.add(organism);
            }
        }

        //sort order by initiative and age
        filteredOrganisms.sort((org1, org2) -> {
            if (org1.getInitiative() != org2.getInitiative()) {
                return Integer.compare(org2.getInitiative(), org1.getInitiative());
            }
            else {
                return Integer.compare(org2.getAge(), org1.getAge());
            }
        });

        for (Organism organism : filteredOrganisms) {
            if (organism.checkIfAlive()) {
                if (organism.getTypeName().equals("Human")) {
                    organism.increaseAge();
                }
                else {
                    organism.action();
                    organism.increaseAge();
                }
            }
        }
        removeDeadOrganisms();

        addLog("----- Turn " + turnNumber + " has ended -----");
        addLog("Press 'S' to save the game.");
        addLog("Press 'L' to load saved game.");
        addLog("Press Spacebar to continue.");

        humanMoved = false;
        turnNumber++;
        drawWorld();
    }

    public void handleKeyPress(int key) {
        if (!hasHumanMoved() && (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)) {
            if (human != null && human.checkIfAlive()) {
                human.action(key);
                humanMoved = true;
                drawWorld();
            }
        }
        else if (key == KeyEvent.VK_SPACE) {
            makeTurn();
        }
    }

    public void pushOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganism(Organism organism) {
        organism.kill();
    }

    public void removeDeadOrganisms() {
        organisms.removeIf(organism -> !organism.isAlive);
        drawWorld();
    }

    public Organism getOrganismPosition(int x, int y) {
        for (Organism organism : organisms) {
            if (organism.getX() == x && organism.getY() == y) {
                return organism;
            }
        }
        return null;
    }

    public Human getHuman(){
        return human;
    }

    public boolean hasHumanMoved() {
        return humanMoved;
    }

    public void setHumanMoved(boolean moved) {
        this.humanMoved = moved;
    }
}
