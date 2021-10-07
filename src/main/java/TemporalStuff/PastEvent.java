package TemporalStuff;

import PeopleStuff.Goblin;

import java.util.ArrayList;
/*
 * Brant Eckert, 11/13/20
 * Interface for an event in the past.
 */
public interface PastEvent {
    /**
     * All the people related to the event
     * @return An arraylist of related goblins
     */
    public ArrayList<Goblin> relevantPeople();

    /**
     * A description of the event.
     * @return The description of the event.
     */
    public String description();

    /**
     *  The day the event occurred
     * @return The date object of the day.
     */
    public Date getDay();
}
