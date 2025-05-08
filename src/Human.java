import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

public class Human extends Animal {
    public Human(int strength, int initiative, int age, int x, int y, World world) {
        super(5, 4, 0, x, y, world);
        world.addLog("Human has been created.");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public String getTypeName() {
        return "Human";
    }

    @Override
    public void action() {}

    public void action(int key) {
        savePrevPos();
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());

        if (neighbouringPositions.isEmpty()) {
            world.addLog("No place to move.");
            return;
        }

        int[] newPos = new int[2];

        boolean hasMoved = movement(key, newPos);

        if (hasMoved) {
            int newX = newPos[0];
            int newY = newPos[1];

            Organism metOrganism = world.getOrganismPosition(newX, newY);
            if (metOrganism != null && metOrganism != this) {
                if (metOrganism.getTypeName() != this.getTypeName()) {
                    collision(metOrganism);
                }
            }

            setPosition(newX, newY);
        }
    }

    @Override
    public void collision(Organism opponent){
        boolean isOpponentAnimal = opponent instanceof Animal;

        if (isOpponentAnimal){
            boolean isTurtle = opponent instanceof Turtle;
            if (isTurtle){
                Turtle turtle = (Turtle) opponent;
                turtle.collision(this);
                return;
            }

            int thisStrength = this.getStrength();
            int opponentStrength = opponent.getStrength();

            if (thisStrength >= opponentStrength){
                world.removeOrganism(opponent);
                world.addLog("Human defeated " + opponent.getTypeName());
            }
            else{
                world.removeOrganism(this);
                world.addLog(opponent.getTypeName() + " defeated Human");
            }
        }
        else{
            String opponentTypeName = opponent.getTypeName();
            if (opponentTypeName.equals("Belladonna") || opponentTypeName.equals("Hogweed")){
                world.removeOrganism(this);
                world.removeOrganism(opponent);
                world.addLog("Human was killed by " + opponent.getTypeName());
            }
            else{
                world.removeOrganism(opponent);
                world.addLog("Human ate " + opponent.getTypeName());
            }
        }
    }

    private boolean movement(int key, int[] newPos) {
        int field = Constants.FIELD_SIZE;
        int newX = getX();
        int newY = getY();

        switch (key) {
            case 38:
                if (getY() - field >= 0) {
                    newY -= field;
                } else {
                    return false;
                }
                break;

            case 40:
                if (getY() + field <= Constants.BOARD_HEIGHT - field) {
                    newY += field;
                } else {
                    return false;
                }
                break;

            case 37:
                if (getX() - field >= 0) {
                    newX -= field;
                } else {
                    return false;
                }
                break;

            case 39:
                if (getX() + field <= Constants.BOARD_WIDTH - field) {
                    newX += field;
                } else {
                    return false;
                }
                break;

            default:
                return false;
        }

        newPos[0] = newX;
        newPos[1] = newY;
        return true;
    }

    public Organism copyOrganism(int x, int y){
        return new Human(5, 4, 0, x, y, world);
    }
}
