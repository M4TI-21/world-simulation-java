import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Animal extends Organism{
    public int strength, initiative, age, x, y, prevX, prevY;
    public Animal(int strength, int initiative, int age, int x, int y, World world){
        super(strength, 0, 0, x, y, world);
    }

    @Override
    public void action() {
        savePrevPos();
        List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());
        Random random = new Random();

        if (neighbouringPositions.isEmpty()){
            world.addLog("No place to move.");
        }

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
                this.collision(met_organism);
            }
        }

        if (met_organism == null ||  met_organism.getTypeName() != this.getTypeName()){
            prevX = x;
            prevY = y;
            setPosition(x, y);
        }
    }

    public void collision(Organism opponent){
        boolean isOpponentAnimal = opponent instanceof Animal;

        if (isOpponentAnimal){
            boolean isTurtle = opponent instanceof Turtle;
            if (isTurtle){
                Turtle turtle = (Turtle) opponent;
                turtle.collision(this);
                return;
            }

            int thisStrength = this.getStrength();
            int opponentStrength = opponent.getStrength();

            if (thisStrength >= opponentStrength){
                world.removeOrganism(opponent);
                world.addLog(this.getTypeName() + " defeated " + opponent.getTypeName());
            }
            else{
                world.removeOrganism(this);
                world.addLog(opponent.getTypeName() + " defeated " + this.getTypeName());
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

    public abstract String getTypeName();
    public abstract void draw(Graphics2D g2d);
    public abstract Organism copyOrganism(int x, int y);
}
