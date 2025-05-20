import java.util.*;
import java.awt.*;
import java.util.List;

public class Hogweed extends Plant {
    public Hogweed(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Sosnowsky's Hogweed has been created");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public String getTypeName() {
        return "Hogweed";
    }

    @Override
    public void action() {
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

        for (List<Integer> position : neighbouringPositions) {
            int newX = position.get(0);
            int newY = position.get(1);

            Organism target = world.getOrganismPosition(newX, newY);
            boolean isOpponentAnimal = target instanceof Animal;

            if (target != null && isOpponentAnimal) {
                world.removeOrganism(target);
                world.addLog(target.getTypeName() + " was killed by Hogweed.");
            }
        }
    }

    @Override
    public void collision(Organism opponent) {
        world.removeOrganism(opponent);
        world.removeOrganism(this);
        world.addLog(opponent.getTypeName() + " was killed by Hogweed.");
    }

    public Organism copyOrganism(int x, int y){
        return new Hogweed(10, 0, 0, x, y, world);
    }

}
