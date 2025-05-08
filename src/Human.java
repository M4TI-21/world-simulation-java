import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Human extends Animal {
    public Human(int strength, int initiative, int age, int x, int y, World world) {
        super(5, 4, 0, x, y, world);
        world.addLog("Human has been created.");
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(getX(), getY(), Constants.FIELD_SIZE, Constants.FIELD_SIZE);
    }

    public String getTypeName() {
        return "Human";
    }

    @Override
    public void action() {
    }

    private boolean movement(int key, int[] newPos) {
        int field = Constants.FIELD_SIZE;
        int newX = x;
        int newY = y;

        switch (key) {
            case KeyEvent.VK_UP:
                if (y - field >= 0) {
                    newY -= field;
                } else {
                    return false;
                }
                break;

            case KeyEvent.VK_DOWN:
                if (y + field <= Constants.BOARD_HEIGHT - field) {
                    newY += field;
                } else {
                    return false;
                }
                break;

            case KeyEvent.VK_LEFT:
                if (x - field >= 0) {
                    newX -= field;
                } else {
                    return false;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (x + field <= Constants.BOARD_WIDTH - field) {
                    newX += field;
                } else {
                    return false;
                }
                break;

            default:
                return false;
        }

        newPos[0] = newX;
        newPos[1] = newY;
        return true;
    }

    @Override
    public void collision(Organism animal) {}

    public Organism copyOrganism(int x, int y){
        return new Human(5, 4, 0, x, y, world);
    }
}
