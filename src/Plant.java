import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Plant extends Organism{
    public int strength, initiative, age, x, y;
    public Plant(int strength, int initiative, int age, int x, int y, World world){
        super(strength, 0, 0, x, y, world);
    }

    public void action(){
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());
        if (neighbouringPositions.isEmpty()){
            world.addLog("No place to sow.");
            return;
        }

        Random random = new Random();

        boolean success = random.nextInt(5) == 0;

        if (success) {
            int position = random.nextInt(neighbouringPositions.size());
            int newX = neighbouringPositions.get(position).get(0);
            int newY = neighbouringPositions.get(position).get(1);

            if (world.getOrganismPosition(newX, newY) == null){
                Organism sowed_plant = this.copyOrganism(newX, newY);
                world.pushOrganism(sowed_plant);
            }
        }
    }

    public void collision(Organism opponent) {
        world.removeOrganism(this);
        world.addLog(opponent.getTypeName() + " ate " + this.getTypeName());
    }

    public abstract void draw(Graphics2D g2d);
    public abstract String getTypeName();
    public abstract Organism copyOrganism(int x, int y);
}
