import java.util.*;
import java.awt.*;

public class Belladonna extends Plant {
    public Belladonna(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Belladonna has been created");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public String getTypeName() {
        return "Belladonna";
    }

    @Override
    public void collision(Organism opponent) {
        world.removeOrganism(this);
        world.removeOrganism(this);
        world.addLog(opponent.getTypeName() + " was killed by Belladonna");
    }

    public Organism copyOrganism(int x, int y){
        return new Belladonna(99, 0, 0, x, y, world);
    }
}
