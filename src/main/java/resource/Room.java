package resource;

/**
 * This Java Bean contains all the info about a room of the gym
 *
 * @author Francesco Caldivezzi
 */
public class Room {
    private final String name;
    private final int slots;

    /**
     * Constructor, with all parameters
     *
     * @param name  the name of the room
     * @param slots the number of available slots the room has
     */
    public Room(String name, int slots) {
        this.name = name;
        this.slots = slots;
    }
    /**
     * Constructor for this class
     *
     * @param name  the name of the room
     */
    public Room(String name) {
        this.name = name;
        this.slots = 0;
    }

    /**
     * Gets the room name
     * @return  the room name
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the slots of the room
     * @return  the slots of the room
     */
    public final int getSlots() {
        return slots;
    }
}
