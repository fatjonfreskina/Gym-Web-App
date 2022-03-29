package resource;

public class Room
{
    private final String name;
    private final int slots;

    public Room(String name,int slots)
    {
        this.name = name;
        this.slots = slots;
    }

    public String getName()
    {
        return name;
    }

    public int getSlots()
    {
        return slots;
    }
}
