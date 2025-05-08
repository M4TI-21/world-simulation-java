import java.util.*;
import java.awt.*;
import java.util.List;

public class Antelope extends Animal {
    public Antelope(int strength, int initiative, int age, int x, int y, World world) {
        super(0, 0, 0, x, y, world);
        world.addLog("Antelope has been created");
    }

    public String getTypeName() {
        return "Antelope";
    }

    @Override
    public void action() {
        List<List<Integer>> neighbouringPositions = new ArrayList<>();
        int x = getX();
        int y = getY();
        int field = Constants.FIELD_SIZE;

        if (x - 2*field >= 0) {
            neighbouringPositions.add(List.of(x - 2*field, y));
        }
        if (x + 2*field <= Constants.BOARD_WIDTH - 2*field) {
            neighbouringPositions.add(List.of(x + 2*field, y));
        }
        if (y - 2*field >= 0) {
            neighbouringPositions.add(List.of(x, y - 2*field));
        }
        if (y + 2*field <= Constants.BOARD_HEIGHT - 2*field) {
            neighbouringPositions.add(List.of(x, y + 2*field));
        }

        Random random = new Random();

        if (neighbouringPositions.isEmpty()){
            world.addLog("No place to move.");
        }

        int position = random.nextInt(neighbouringPositions.size());
        int newX = neighbouringPositions.get(position).get(0);
        int newY = neighbouringPositions.get(position).get(1);

        prevX = x;
        prevY = y;
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

    @Override
    public void collision(Organism opponent){
        Random random = new Random();
        boolean escape = random.nextInt(2) == 0;
        boolean isOpponentAnimal = opponent instanceof Animal;

        if (isOpponentAnimal){
            if (escape) {
                world.addLog("Antelope ran away from fight with " + opponent.getTypeName());
                List<List<Integer>> neighbouringPositions = findNeighbouringPos(getX(), getY());

                int position = random.nextInt(neighbouringPositions.size());
                int newX = neighbouringPositions.get(position).get(0);
                int newY = neighbouringPositions.get(position).get(1);
                setPosition(newX, newY);
                return;
            }
            else {
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
        g2d.setColor(Color.yellow);
        g2d.fillOval(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public Organism copyOrganism(int x, int y){
        return new Guarana(4, 4, 0, x, y, world);
    }
}
