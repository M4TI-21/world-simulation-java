import java.util.*;
import java.awt.*;
import java.util.List;

public class Fox extends Animal {
    public Fox(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Fox has been created");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.orange);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public String getTypeName() {
        return "Fox";
    }

    @Override
    public void action() {
        savePrevPos();
        //fox won't move to a field occupied by stronger organism
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());
        List<List<Integer>> safePositions = new ArrayList<>();

        for (List<Integer> pos : neighbouringPositions) {
            int posX = pos.get(0);
            int posY = pos.get(1);
            Organism opponent = world.getOrganismPosition(posX, posY);
            if (opponent == null || opponent.getStrength() <= getStrength()) {
                safePositions.add(pos);
            }
        }

        if (safePositions.isEmpty()) {
            world.addLog("Fox has no safe place to move.");
            return;
        }
        Random random = new Random();

        int position = random.nextInt(safePositions.size());
        int newX = safePositions.get(position).get(0);
        int newY = safePositions.get(position).get(1);
        x = newX;
        y = newY;

        Organism met_organism = world.getOrganismPosition(x, y);
        //breeding
        if (met_organism != null && met_organism != this){
            if (met_organism.getTypeName() == this.getTypeName()){
                safePositions.remove(position);
                if (safePositions.isEmpty()){
                    world.addLog("No space for new animal");
                    return;
                }
                else{
                    //add new organism to random neighbouring position
                    position = random.nextInt(safePositions.size());
                    newX = safePositions.get(position).get(0);
                    newY = safePositions.get(position).get(1);

                    Organism new_animal = this.copyOrganism(newX, newY);
                    world.pushOrganism(new_animal);
                    return;
                }
            }
            else{
                collision(met_organism);
            }
        }

        if (met_organism == null ||  met_organism.getTypeName() != this.getTypeName()){
            prevX = x;
            prevY = y;
            setPosition(x, y);
        }
    }

    public Organism copyOrganism(int x, int y){
        return new Fox(3, 7, 0, x, y, world);
    }
}
