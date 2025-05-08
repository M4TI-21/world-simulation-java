import java.util.*;
import java.awt.*;
import java.util.List;

public class Turtle extends Animal {
    public Turtle(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Turtle has been created");
    }

    public String getTypeName() {
        return "Turtle";
    }

    @Override
    public void action() {
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());
        Random random = new Random();

        if (neighbouringPositions.isEmpty()){
            world.addLog("No place to move.");
        }

        int success = random.nextInt(4);
        if (success == 0){
            int position = random.nextInt(neighbouringPositions.size());
            int newX = neighbouringPositions.get(position).get(0);
            int newY = neighbouringPositions.get(position).get(1);
            x = newX;
            y = newY;

            Organism met_organism = world.getOrganismPosition(x, y);
            //breeding
            if (met_organism != null && met_organism != this){
                if (met_organism.getTypeName() == this.getTypeName()){
                    neighbouringPositions.remove(position);
                    if (neighbouringPositions.isEmpty()){
                        world.addLog("No space for new animal");
                        return;
                    }
                    else{
                        //add new organism to random neighbouring position
                        position = random.nextInt(neighbouringPositions.size());
                        newX = neighbouringPositions.get(position).get(0);
                        newY = neighbouringPositions.get(position).get(1);

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
    }

    @Override
    public void collision(Organism opponent){
        boolean isOpponentAnimal = opponent instanceof Animal;

        if (isOpponentAnimal){
            int thisStrength = this.getStrength();
            int opponentStrength = opponent.getStrength();

            if (opponentStrength < 5) {
                world.addLog(opponent.getTypeName() + "was reflected by Turtle");
                opponent.setPosition(opponent.getPrevX(), opponent.getPrevY());
                return;
            }
            else{
                if (thisStrength >= opponentStrength){
                    world.removeOrganism(opponent);
                    world.addLog(this.getTypeName() + " defeated " + opponent.getTypeName());
                }
                else{
                    world.removeOrganism(this);
                    world.addLog(opponent.getTypeName() + " defeated " + this.getTypeName());
                }
            }
        }
        else{
            String opponentTypeName = opponent.getTypeName();
            if (opponentTypeName.equals("Belladonna") || opponentTypeName.equals("Hogweed")){
                world.removeOrganism(this);
                world.removeOrganism(opponent);
                world.addLog(this.getTypeName() + " was killed by " + opponent.getTypeName());
            }
            else{
                world.removeOrganism(opponent);
                world.addLog(this.getTypeName() + " ate " + opponent.getTypeName());
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Turtle(2, 1, 0, x, y, world);
    }
}
