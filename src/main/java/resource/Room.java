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

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSlots(int slots)
    {
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
