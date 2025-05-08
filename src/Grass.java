import java.util.*;
import java.awt.*;

public class Grass extends Plant {
    public Grass(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Grass has been created");
    }

    public String getTypeName() {
        return "Grass";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Grass(0, 0, 0, x, y, world);
    }

}
