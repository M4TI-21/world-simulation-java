import java.util.*;
import java.awt.*;
import java.util.List;

public class SowThistle extends Plant {
    public SowThistle(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Sow thistle has been created");
    }

    public String getTypeName() {
        return "Sow thistle";
    }

    public void action(){
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());
        if (neighbouringPositions.isEmpty()){
            world.addLog("No place to sow.");
            return;
        }

        Random random = new Random();

        for (int i = 0; i < 3; i++){
            boolean success = random.nextInt(5) == 0;
            if (success) {
                int position = random.nextInt(neighbouringPositions.size());
                int newX = neighbouringPositions.get(position).get(0);
                int newY = neighbouringPositions.get(position).get(1);

                if (world.isPositionFree(newX, newY)){
                    Organism sowed_plant = this.copyOrganism(newX, newY);
                    world.pushOrganism(sowed_plant);
                }
                neighbouringPositions.remove(position);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.yellow);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new SowThistle(0, 0, 0, x, y, world);
    }
}
