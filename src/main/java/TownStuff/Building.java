package TownStuff;
/*
 * Brant Eckert, 11/11/20
 * Interface for a building.
 */
public interface Building {
    /**
     * Description of a building
     * @return The description
     */
    public String getDescription();

    /**
     *  Name for a building
     * @return Name
     */
    public String getName();

    /**
     * New name for a building
     * @param n The new name
     */
    public void setName(String n);

    /**
     * The function the building performs; will typically reference other values and classes posssibly businesses.
     */
    public void function();

}
