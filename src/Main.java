import java.util.*;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();
        World world = frame.getWorld();

        world.addOrganism("Grass");
        world.addOrganism("Grass");
        world.addOrganism("Grass");
        world.addOrganism("Sow thistle");
        world.addOrganism("Guarana");
        world.addOrganism("Guarana");
        world.addOrganism("Guarana");
        world.addOrganism("Belladonna");
        world.addOrganism("Belladonna");
        world.addOrganism("Belladonna");
        world.addOrganism("Hogweed");
        world.addOrganism("Hogweed");
        world.addOrganism("Hogweed");


        world.addOrganism("Sheep");
        world.addOrganism("Sheep");
        world.addOrganism("Sheep");
        world.addOrganism("Wolf");
        world.addOrganism("Wolf");
        world.addOrganism("Wolf");
        world.addOrganism("Fox");
        world.addOrganism("Fox");
        world.addOrganism("Fox");
        world.addOrganism("Turtle");
        world.addOrganism("Turtle");
        world.addOrganism("Turtle");
        world.addOrganism("Antelope");
        world.addOrganism("Antelope");
        world.addOrganism("Antelope");

        world.drawWorld();
    }

}