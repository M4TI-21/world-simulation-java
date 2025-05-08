import java.util.*;
import java.awt.*;

public class Wolf extends Animal {
    public Wolf(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Wolf has been created");
    }


    @Override
    public void collision(Organism animal) {
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.gray);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE - 1, Constants.FIELD_SIZE - 1);
    }
}
