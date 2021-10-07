package TownStuff;

import Misc.General;
import PeopleStuff.Goblin;
import PeopleStuff.Names;

import java.util.ArrayList;
import java.util.function.Predicate;
/*
 * Brant Eckert, 11/13/20
 * A house that houses goblins
 */
public class House implements Building {
    //VARS
    private Town town = null;
    private String name = "";
    private String unmodName = "";
    private String description = "";
    private int residentCap = 0;
    private int curResidents = 0;
    protected ArrayList<Goblin> residents = new ArrayList<>();
    private Predicate<Goblin> residentConstraint = null;

    //CONSTRUCTORS
    public House(String inName, int inResidentCap){
        unmodName = inName;
        residentCap = inResidentCap;
        updateName();
        updateDescription();
    }

    public House(String inName, int inResidentCap, Predicate<Goblin> inResConstraint){
        this(inName, inResidentCap);
        residentConstraint = inResConstraint;
    }
    public House(String inName, int inResidentCap, Predicate<Goblin> inResConstraint, Town iTown){
        this(inName, inResidentCap, inResConstraint);
        town = iTown;
    }

    public House(String inName, int inResidentCap, ArrayList<Goblin> inResidents){
        residentCap = inResidentCap;
        for(Goblin i : inResidents){
            if(curResidents < residentCap){
                residents.add(i);
                curResidents++;
            }
        }
        unmodName = inName;
        updateName();
        updateDescription();
    }

    public House(String inName, int inResidentCap, ArrayList<Goblin> inResidents, Town iTown) {
        this(inName, inResidentCap, inResidents);
        town = iTown;
    }

    public House(String inName, int inResidentCap, ArrayList<Goblin> inResidents, Predicate<Goblin> inResConstraint){
        this(inName, inResidentCap, inResidents);
        residentConstraint = inResConstraint;
        for(Goblin i : residents){
            if(!checkConstraint(i))
                evict(i);
        }
        updateName();
    }
    public House(String inName, int inResidentCap, ArrayList<Goblin> inResidents, Predicate<Goblin> inResConstraint, Town iTown){
        this(inName, inResidentCap, inResidents, inResConstraint);
        town = iTown;
    }



        //HOUSE METHODS
    public Town getTown(){
        return town;
    }
    public void setTown(Town n){
        town = n;
    }

    public ArrayList<Goblin> getResidentList(){
        return new ArrayList<>(residents);
    }

    /**
     * Get the info on the house
     * @return The info about the house including the name, description and a list of the residents.
     */
    public String houseInfo(){
        return this.getName() + General.charRep(20, '-') + "\n" + this.getDescription() + "\n" + strResidentList();
    }

    /**
     * Lists all the residents of the house.
     * @return A list of the residents.
     */
    public String strResidentList(){
        String end = "";
        int index = 1;
        for(Goblin i : residents){
            end += index + ": " + i.getFullName() + "\n";
            index++;
        }
        return end;
    }


    /**
     * Evict a resident
     * @param n the goblin to evict
     * @return the success of the eviction
     */
    public boolean evict(Goblin n){
        boolean success = false;
        success = residents.remove(n);
        if(success) {
            curResidents--;
            if(town != null)
            town.updateHomeStatus(n, false);
        }
        return success;
    }

    /**
     * Evict a goblin in a specific position on the resident list
     * @param pos The position of the goblin
     * @return The success of the eviction
     */
    public boolean evict(int pos){
        if(residents.get(pos) == null)
            return false;
        boolean success = false;
        Goblin evictee = residents.remove(pos);
        if(evictee != null) {
            curResidents--;
            if(town != null)
            town.updateHomeStatus(evictee, false);
        }
        return success;
    }

    /**
     * MakeHomeless is Evict but it returns the goblin you're removing.
     * @param pos Position of the goblin to remove
     * @return The homeless goblin
     */
    public Goblin makeHomeless(int pos){
        Goblin hobo =  residents.remove(pos);
        if(hobo != null) {
            curResidents--;
            if(town != null)
            town.updateHomeStatus(hobo, false);
        }
        return hobo;
    }
    /**
     * MakeHomeless is Evict but it returns the goblin you're removing.
     * @param n The removed goblin.
     * @return The homeless goblin
     */
    public Goblin makeHomeless(Goblin n){
        Goblin hobo = n;
        boolean success = residents.remove(n);
        if(!success)
            return null;
        curResidents--;

        if(town != null)
        town.updateHomeStatus(hobo, false);
        return hobo;
    }

    /**
     * Move a goblin in
     * @param n The goblin to move in
     * @param primary_resident Whether or not they are the primary resident
     * @return Success of moving in.
     */
    public boolean moveIn(Goblin n, boolean primary_resident){
        if(!checkConstraint(n))
            return false;
        if(curResidents >= residentCap)
            return false;
        boolean success = false;
        if(primary_resident) {
            for (int i = curResidents - 1; i != 0; i--) {
                residents.set(i + 1, residents.get(i));
            }
            residents.set(0, n);
            updateName();
            success = true;
        }
        else {
            success = residents.add(n);
        }
        if(success) {
            curResidents++;
            if(town != null)
            town.updateHomeStatus(n, true);
        }
        return success;
    }

    /**
     * Move in if the goblin coincides with the constraints
     * @param n the goblin to move in
     * @return The success in the move in
     */
    public boolean moveIn(Goblin n){
        if(!checkConstraint(n))
            return false;
        if(curResidents >= residentCap)
            return false;
        boolean success = residents.add(n);
        if(success) {
            curResidents++;
            if(town != null)
                town.updateHomeStatus(n, true);
        }
        return success;
    }

    public boolean isFull(){
        return curResidents >= residentCap;
    }

    public boolean checkConstraint(Goblin n){
        if(residentConstraint == null)
            return true;
        else
            return residentConstraint.test(n);
    }

    /**
     * Update Constraint
     * @param n Condition for goblin being correct
     * @return Kicked out or not
     */
    public int updateConstraint(Predicate<Goblin> n){
        int kickedOut = 0;
        residentConstraint = n;
        for(Goblin i : residents){
            if(!checkConstraint(i)){
                evict(i);
                kickedOut++;
            }
        }
        return kickedOut;
    }


    // OVERRIDE METHODS
    @Override
    public String getDescription() {
        updateDescription();
        return description;
    }

    public void updateDescription() {
        description = "A";
        boolean n_or_not = false;
        for(char i : Names.VOWELS){
            if(unmodName.charAt(0) == i)
                n_or_not = true;
        }
        if(n_or_not)
            description+="n";
        description += " " + unmodName + " that can house " + residentCap + " goblins. Currently at " + curResidents + "/" + residentCap + ".";
    }

    @Override
    public String getName() {
        updateName();
        return name;
    }

    public void updateName(){
        if(curResidents != 0)
            name = residents.get(0).getFullName() + "'s " + unmodName;
        else
            name = "Empty " + unmodName;
    }

    @Override
    public void setName(String n) {
        unmodName = n;
        updateName();
    }

    /**
     * To be used in coordination with a town.
     */
    @Override
    public void function() {
        String funcDef = "Housing the residents of a town.";
    }
}
