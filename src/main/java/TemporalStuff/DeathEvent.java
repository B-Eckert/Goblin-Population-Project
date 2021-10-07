package TemporalStuff;

import PeopleStuff.Goblin;

import java.util.ArrayList;

/*
 * Brant Eckert, 11/13/20
 * Complex class for a death of a goblin; Currently unused. Implements PastEvent, something that happened in the past.
 */

public class DeathEvent implements PastEvent {
    Goblin deceased = null;
    Goblin killer = null;
    String description = "";
    Date day = null;

    public DeathEvent(Goblin n, Date nday){
        deceased = n;
        description = " passed away ";
        day = nday;
    }

    public DeathEvent(Goblin killed, Goblin nkiller, Date nday){
        deceased = killed;
        killer = nkiller;
        day = nday;
        description = " was killed by " + nkiller.getFullName() + " ";
    }

    @Override
    public ArrayList<Goblin> relevantPeople() {
        ArrayList<Goblin> n = new ArrayList<>();
        n.add(deceased);
        if(killer != null)
            n.add(killer);
        return n;
    }

    @Override
    public String description() {
        return deceased.getFullName() + description + "in " + day.toString();
    }

    @Override
    public Date getDay() {
        return day;
    }
}
