import java.util.*;
import java.awt.*;

public class Guarana extends Plant {
    public Guarana(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Guarana has been created");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public String getTypeName() {
        return "Guarana";
    }

    @Override
    public void collision(Organism opponent) {
        opponent.increaseStrength();
        world.removeOrganism(this);
        world.addLog(opponent.getTypeName() + " ate " + this.getTypeName());
        world.addLog(opponent.getTypeName() + "'s strength has increased");
    }

    public Organism copyOrganism(int x, int y){
        return new Guarana(0, 0, 0, x, y, world);
    }
}
