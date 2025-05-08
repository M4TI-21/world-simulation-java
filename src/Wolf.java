import java.util.*;
import java.awt.*;

public class Wolf extends Animal {
    public Wolf(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Wolf has been created");
    }

    public String getTypeName() {
        return "Wolf";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.gray);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Wolf(9, 5, 0, x, y, world);
    }
}
