package resource;

public class Room
{
    private String name;
    private int slots;

    public Room(String name,int slots)
    {
        this.name = name;
        this.slots = slots;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    private void setSlots(int slots)
    {
        this.slots = slots;
    }

    private String getName()
    {
        return name;
    }

    private int getSlots()
    {
        return slots;
    }
}
