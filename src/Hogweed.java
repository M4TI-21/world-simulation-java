import java.util.*;
import java.awt.*;

public class Hogweed extends Plant {
    public Hogweed(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Sosnowsky's Hogweed has been created");
    }

    @Override
    public void action() {
    }

    @Override
    public void collision(Organism animal) {
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE - 1, Constants.FIELD_SIZE - 1);
    }
}
