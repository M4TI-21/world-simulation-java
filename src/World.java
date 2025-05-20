import javax.swing.*;
import java.util.*;
import java.io.*;
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
    private boolean isHumanAlive = false;

    public Organism selectedOrganism;
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
            case "Sow_thistle":
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
                isHumanAlive = true;
                break;
        }

        if (newOrganism != null) {
            organisms.add(newOrganism);
            drawWorld();
        }
    }

    public List<Organism> getOrganismsList() {
        return new ArrayList<>(organisms);
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

            isFree = getOrganismPosition(x, y) == null;
        }

        return new int[]{x, y};
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
                    addLog("Waiting for human movement.");
                    addLog(human.abilityStatus());
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
        if (!hasHumanMoved() && (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_A)) {
            if (human != null && human.checkIfAlive()) {
                human.action(key);
                humanMoved = true;
                drawWorld();
            }
        }
        else if (key == KeyEvent.VK_SPACE) {
            makeTurn();
        }
        else if (key == KeyEvent.VK_S) {
            saveGame();
        }
        else if (key == KeyEvent.VK_L) {
            loadGame();
        }
    }

    public void pushOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganism(Organism organism) {
        if (organism instanceof Human) {
            Human human = (Human) organism;
            if (human.isAbilityActive()) {
                return;
            }
        }

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

    public void selectOrganism(String type) {
        for (Organism organism : organisms) {
            if (organism.getTypeName().equals(type)) {
                selectedOrganism = organism;
                addLog("Selected organism: " + type);
                return;
            }
        }
    }

    public void addOrganismOnClick(int x, int y) {
        if (selectedOrganism == null) {
            addLog("No organism selected.");
            return;
        }
        x = x * Constants.FIELD_SIZE;
        y = y * Constants.FIELD_SIZE;
        Organism organism = getOrganismPosition(x, y);
        if (organism != null) {
            addLog("Field already occupied.");
            return;
        }
        else{
            Organism new_organism = selectedOrganism.copyOrganism(x, y);
            pushOrganism(new_organism);
            addLog("Added " + new_organism.getTypeName() + " at (" + x + ", " + y + ")");
            drawWorld();
            return;
        }
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter("save.txt")) {
            for (Organism org : organisms) {
                if (org instanceof Human) {
                    Human human = (Human) org;
                    writer.printf("%s %d %d %d %d %d %d %d\n",
                            human.getTypeName(),
                            human.getStrength(),
                            human.getInitiative(),
                            human.getAge(),
                            human.getX(),
                            human.getY(),
                            human.getAbilityActive(),
                            human.getAbilityCooldown()
                    );
                } else {
                    writer.printf("%s %d %d %d %d %d\n",
                            org.getTypeName(),
                            org.getStrength(),
                            org.getInitiative(),
                            org.getAge(),
                            org.getX(),
                            org.getY()
                    );
                }
            }
            addLog("Game has been saved.");
        }
        catch (IOException e) {
            addLog("Error while saving game.");
            e.printStackTrace();
        }
    }

    public void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) {
            addLog("There is no saved game.");
            return;
        }

        //Clear current organisms
        organisms.clear();
        human = null;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] data = line.split(" ");
                String typeName = data[0];

                int strength = Integer.parseInt(data[1]);
                int initiative = Integer.parseInt(data[2]);
                int age = Integer.parseInt(data[3]);
                int x = Integer.parseInt(data[4]);
                int y = Integer.parseInt(data[5]);

                Organism organism = null;
                switch (typeName) {
                    case "Sheep":
                        organism = new Sheep(strength, initiative, age, x, y, this);
                        break;
                    case "Wolf":
                        organism = new Wolf(strength, initiative, age, x, y, this);
                        break;
                    case "Fox":
                        organism = new Fox(strength, initiative, age, x, y, this);
                        break;
                    case "Turtle":
                        organism = new Turtle(strength, initiative, age, x, y, this);
                        break;
                    case "Antelope":
                        organism = new Antelope(strength, initiative, age, x, y, this);
                        break;
                    case "Grass":
                        organism = new Grass(strength, initiative, age, x, y, this);
                        break;
                    case "Sow_thistle":
                        organism = new SowThistle(strength, initiative, age, x, y, this);
                        break;
                    case "Guarana":
                        organism = new Guarana(strength, initiative, age, x, y, this);
                        break;
                    case "Belladonna":
                        organism = new Belladonna(strength, initiative, age, x, y, this);
                        break;
                    case "Hogweed":
                        organism = new Hogweed(strength, initiative, age, x, y, this);
                        break;
                    case "Human":
                        int abilityActive = Integer.parseInt(data[6]);
                        int abilityCooldown = Integer.parseInt(data[7]);
                        Human human = new Human(strength, initiative, age, x, y, this);
                        human.setAbilityActive(abilityActive);
                        human.setAbilityCooldown(abilityCooldown);
                        organism = human;
                        this.human = human;
                        break;
                }

                if (organism != null) {
                    pushOrganism(organism);
                }
            }

            this.turnNumber = 1;
            addLog("Game has been loaded.");
            drawWorld();
        }
        catch (Exception e) {
            addLog("Error while loading game.");
            e.printStackTrace();
        }
    }
}
