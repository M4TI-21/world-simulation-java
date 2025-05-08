import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Animal extends Organism{
    public int strength, initiative, age, x, y, prevX, prevY;
    public Animal(int strength, int initiative, int age, int x, int y, World world){
        super(strength, 0, 0, x, y, world);
    }

    @Override
    public void action() {
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());
        Random random = new Random();

        if (neighbouringPositions.isEmpty()){
            world.addLog("No place to move.");
        }

        int position = random.nextInt(neighbouringPositions.size());
        int newX = neighbouringPositions.get(position).get(0);
        int newY = neighbouringPositions.get(position).get(1);

        prevX = x;
        prevY = y;
        x = newX;
        y = newY;

        setPosition(x, y);
    }
    public void collision(Organism animal){}


    public void increaseStrength() {
        this.strength += 3;
    }

    public abstract String getTypeName();
    public abstract void draw(Graphics2D g2d);
    public abstract Organism copyOrganism(int x, int y);
}
