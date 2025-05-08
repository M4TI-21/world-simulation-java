import java.util.*;
import java.awt.*;

public class Sheep extends Animal {
    public Sheep(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Sheep has been created");
    }

    public String getTypeName() {
        return "Sheep";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Sheep(4, 4, 0, x, y, world);
    }
}
