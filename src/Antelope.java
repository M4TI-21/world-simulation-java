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

        setPosition(x, y);
    }

    @Override
    public void collision(Organism animal) {
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
