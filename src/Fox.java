import java.util.*;
import java.awt.*;

public class Fox extends Animal {
    public Fox(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Fox has been created");
    }

    public String getTypeName() {
        return "Fox";
    }

    @Override
    public void collision(Organism animal) {
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.orange);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Fox(3, 7, 0, x, y, world);
    }
}
