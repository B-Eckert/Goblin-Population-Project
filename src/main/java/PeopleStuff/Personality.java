package PeopleStuff;

import DataStructs.Tuple;
import Misc.*;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Brant Eckert, 11/8/20
 * Personality class with 10 fields; Bravery, Intelligence, Strength, Cunning, Religiosity, Cruelty, Honor, Attractiveness and Promiscuity.
 * All personalities can nbe between -200 and 200.
 */

public class Personality{
    /**
     * just gonna copy this:
     *             "Bravery",                  // 0: determines bravery (positive) or cowardliness (negative). More prone to boldness if high, fleeing if low.
     *             "Intelligence",             // 1: intelligence (positive) or stupidity (negative). Learning comes easy if high.
     *             "Strength",                 // 2: strength (positive) or weakness (negative) determines physical strength and how they fare in combat
     *             "Cunning",                  // 3: cunning (positive) or honesty (negative) determines how they act in combat, and whether they favor bold or sneaky strats.
     *             "Charisma",                 // 4: charismaticness (positive) or ineloquence (negative) determines speech-based checks.
     *             "Religiosity",              // 5: religiosity (positive) or athiesm (negative) determines what their emphasis or value on the gods are.
     *             "Cruelty",                  // 6: cruelty (positive) or kindness (negative) determines how horrible this person is.
     *             "Honor",                    // 7: honor (positive) or dishonor (negative) determines the values of the person
     *             "Attractiveness",           // 8: attractiveness (positive) or ugliness (negative) determines physical appearance and finding a mate might be easier if higher.
     *             "Promiscuity"               // 9: promiscuity (positive) or celibacy (negative) determines the frequentness of intercourse and ergo children count.
     */
    public static final ArrayList<String> TRAITS = new ArrayList<>(Arrays.asList(
            "Bravery",                  // 0: determines bravery (positive) or cowardliness (negative). More prone to boldness if high, fleeing if low.
            "Intelligence",             // 1: intelligence (positive) or stupidity (negative). Learning comes easy if high.
            "Strength",                 // 2: strength (positive) or weakness (negative) determines physical strength and how they fare in combat
            "Cunning",                  // 3: cunning (positive) or honesty (negative) determines how they act in combat, and whether they favor bold or sneaky strats.
            "Charisma",                 // 4: charismaticness (positive) or ineloquence (negative) determines speech-based checks.
            "Religiosity",              // 5: religiosity (positive) or athiesm (negative) determines what their emphasis or value on the gods are.
            "Cruelty",                  // 6: cruelty (positive) or kindness (negative) determines how horrible this person is.
            "Honor",                    // 7: honor (positive) or dishonor (negative) determines the values of the person
            "Attractiveness",           // 8: attractiveness (positive) or ugliness (negative) determines physical appearance and finding a mate might be easier if higher.
            "Promiscuity"               // 9: promiscuity (positive) or celibacy (negative) determines the frequentness of intercourse and ergo children count.
            // more TBD
    ));

    /**
     * Generates a random personality.
     * @return
     * Returns that randomly generated personality.
     */
    public static Personality genRandom(){
        ArrayList<Tuple<String, Integer>> gen = new ArrayList<>();
        for(String i : TRAITS){
            gen.add(new Tuple<>(i, Dice.roll(-200, 200)));
        }
        return new Personality(gen);
    }

    private ArrayList<Tuple<String, Integer>> personalityValues = new ArrayList<>();
    public ArrayList<Tuple<String, Integer>> getPersonalityValues(){
        return personalityValues;
    }
    public int getPersonalityValue(String name){
        for(Tuple<String, Integer> i : personalityValues){
            if(i.getA().equals(name)){
                return i.getB();
            }
        }
        return -1;
    }

    /**
     * Initializes a new personality
     * Anything not included in the parameter will be tacked on at the end w/ a random value.
     * @param inValues
     * All the values perfectly intact before being inserted.
     * Meant for internal use by other personality-generating functions.
     * Also ensures that they're all traits that exist in the normal function.
     */
    protected Personality(ArrayList<Tuple<String, Integer>> inValues){
        ArrayList<String> unusedTraits = General.cloneArray(TRAITS);
        for(Tuple<String, Integer> i : inValues) {
            if(unusedTraits.remove(i.getA())) {
                if(i.getB() > 200)
                    i.setB(200);
                else if(i.getB() < -200)
                    i.setB(-200);
                personalityValues.add(i);
            }
        }
        if(!unusedTraits.isEmpty()){
            for(String i : unusedTraits){
                personalityValues.add(new Tuple<>(i, Dice.roll(-200, 200)));
            }
        }
    }

    /**
     * Produces the personality in a readable format.
     * @return
     * Returns a readable fucking string.
     */
    public String toString(){
        String end = "{ ";
        for(Tuple<String, Integer> i : personalityValues){
            end += i.getA() + ":" + i.getB() + "; ";
        }
        end += "}";
        return end;
    }
}
