import java.util.List;
import java.util.ArrayList;
import java.awt.*;

public abstract class Organism {
    private int strength, initiative, age, x, y, prevX, prevY;
    protected World world;

    public Organism(int strength, int initiative, int age, int x, int y, World world) {
        this.strength = strength;
        this.initiative = initiative;
        this.age = age;
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.world = world;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public abstract String getTypeName();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAge() {
        return age;
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public List<List<Integer>> findNeighbouringPos(int x, int y) {
        List<List<Integer>> neighbouringPositions = new ArrayList<>();

        int field = Constants.FIELD_SIZE;

        if (x - field >= 0) {
            neighbouringPositions.add(List.of(x - field, y));
        }
        if (x + field <= Constants.BOARD_WIDTH - field) {
            neighbouringPositions.add(List.of(x + field, y));
        }
        if (y - field >= 0) {
            neighbouringPositions.add(List.of(x, y - field));
        }
        if (y + field <= Constants.BOARD_HEIGHT - field) {
            neighbouringPositions.add(List.of(x, y + field));
        }

        return neighbouringPositions;
    }

    void increaseAge() {
        this.age += 1;
    }


    public abstract void draw(Graphics2D g2d);
    public abstract void action();
    public abstract Organism copyOrganism(int x, int y);
    public abstract void collision(Organism animal);

}
