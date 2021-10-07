package TownStuff;

import DataStructs.Tuple;
import PeopleStuff.Goblin;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.function.Predicate;
/*
 * Brant Eckert, 12/19/20
 * A town is just a bunch of citizens, goblins and buildings. Also records the housing records for each citizen.
 */
public class Town {
    // VARIABLES
    private ArrayList<Building> contents = new ArrayList<>();
    private ArrayList<Goblin> citizens = new ArrayList<>();
    private ArrayList<Tuple<Goblin, Boolean>> housedStatus = new ArrayList<>();
    private Predicate<Goblin> citReq = null;
    private String name = "";
    private String description = "";
    private int population = 0;
    private int popCap = 0;

    //CONSTRUCTOR
    public Town(String inName){
        name = inName;
        updateDesc();
    }
    public Town(String inName, Predicate<Goblin> inCitReq){
        this(inName);
        citReq = inCitReq;
    }
    public Town(String inName, ArrayList<Goblin> inCitizens){
        this(inName);
        for(Goblin i : inCitizens){
            if(population < popCap) {
                citizens.add(i);
                housedStatus.add(new Tuple<>(i, false));
                population++;
            }
            else
                break;
        }

    }
    public Town(String inName, Predicate<Goblin> inCitReq, ArrayList<Goblin> inCitizens){
        this(inName, inCitReq);
        for(Goblin i : inCitizens){
            if(population < popCap && checkCitizenship(i)) {
                citizens.add(i);
                housedStatus.add(new Tuple<>(i, false));
                population++;
            }
            else
                break;
        }
    }
    // TOWN METHODS

    /**
     * Houses all the unhoused goblins.
     * @param n A goblin that may or may not be unhoused
     * @param status Housed or not housed.
     * @return Success of the update method.
     */
    public boolean updateHomeStatus(Goblin n, boolean status){
        Tuple<Goblin, Boolean> entry = null;
        for(Tuple<Goblin, Boolean> i : housedStatus){
            if(i.getA().equals(n)) {
                i.setB(status);
                entry = i;
                break;
            }
        }
        if(entry != null)
            return true;
        return false;
    }

    /**
     * Immigrate the goblin to here.
     * @param n Goblin to add to the town.
     * @return Returns true if successful.
     */
    public boolean immigrate(Goblin n){
        if(population >= popCap || !checkCitizenship(n))
            return false;
        return forceImmigrate(n);
    }

    /**
     * Forcibly immigrate the goblin. irrespective of population cap
     * @param n The goblin to immigrate
     * @return True if successful.
     */
    public boolean forceImmigrate(Goblin n){
        boolean success = citizens.add(n);
        if(success) {
            population++;
            housedStatus.add(new Tuple<>(n, false));
        }
        return success;
    }

    /**
     * Banishes a goblin from the town.
     * @param n Goblin to banish.
     * @return Returns a tuple that is the goblin and its housed status.
     */
    public Tuple<Goblin, Boolean> banish(Goblin n){
        boolean success = citizens.remove(n);
        if(success){
            for(Tuple<Goblin, Boolean> i : housedStatus){
                if(i.getA().equals(n)) {
                    housedStatus.remove(i);
                    break;
                }
            }
            population--;
            return new Tuple<>(n, success);
        }
        return new Tuple<>(null, success);
    }

    private void updateDesc(){
        description = "The Town of " + name + ", Population " + population + "/" + popCap + ".";
    }
    public boolean checkCitizenship(Goblin n){
        if(citReq == null)
            return true;
        else
            return citReq.test(n);
    }

    /**
     * Attempts to house all homeless goblins in any available house.
     * @return
     * Returns an integer that:
     * If positive, represents the number of homeless goblins.
     * If negative, represents the number of unfilled houses. (Implying all goblins have a home.)
     */
    public int attemptHouse(){
        ArrayList<House> houses = new ArrayList<>();
        for(Building i : contents){
            if(i instanceof House) {
                House a = (House) i;
                if(!a.isFull())
                    houses.add(a);
            }
        }
        ArrayList<Goblin> homelessGoblins = new ArrayList<>();
        for(Tuple<Goblin, Boolean> i : housedStatus){
            if(!i.getB())
                homelessGoblins.add(i.getA());
        }

        if(houses.isEmpty()) {
            return homelessGoblins.size();
        }
        else if (homelessGoblins.isEmpty()){
            return -1 * houses.size();
        }

        for(Goblin i : homelessGoblins){
            for(House j : houses){
                if(j.checkConstraint(i) && !j.isFull()){
                    boolean success = j.moveIn(i);
                    if(success){
                        homelessGoblins.remove(i);
                        if(j.isFull()){
                            houses.remove(j);
                        }
                        break;
                    }
                }
            }
        }
        return homelessGoblins.size();
    }
    //TODO: housing, resources, fun stuff like that :D
    public ArrayList<Goblin> getCitizens(){
        return citizens;
    }
}
