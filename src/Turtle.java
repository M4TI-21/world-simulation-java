import java.util.*;
import java.awt.*;

public class Turtle extends Animal {
    public Turtle(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Turtle has been created");
    }

    public String getTypeName() {
        return "Turtle";
    }

    @Override
    public void collision(Organism animal) {
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Turtle(2, 1, 0, x, y, world);
    }
}
