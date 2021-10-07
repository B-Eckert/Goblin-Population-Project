package Misc;

import DataStructs.Tuple;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/*
 * Brant Eckert, 12/19/20
 * A toolbox of useful functions and things that I might need to use.
 */
public class General {
    /**
     * Clones an arraylist of type T
     * @param n the arraylist to clone
     * @param <T> The type
     * @return A cloned array
     */
    public static <T> ArrayList<T> cloneArray(ArrayList<T> n){
        ArrayList<T> result = new ArrayList<>();
        for(T i : n)
            result.add(i);
        return result;
    }

    /**
     * Combines two arraylists of type T
     * @param a ArrayList A
     * @param b ArrayList B
     * @param <T> The type
     * @return The combined arraylist
     */
    public static <T> ArrayList<T> unionArray(ArrayList<T> a, ArrayList<T> b){
        ArrayList<T> result = new ArrayList<>();
        for(T i : a)
            result.add(i);
        for(T i: b)
            result.add(i);
        return result;
    }

    /**
     * Creates a string with a number of tabs.
     * @param tabNum The number of tabs to put on.
     * @return The tabbed out string.
     */
    public static String tabs(int tabNum){
        String end = "";
        while(tabNum != 0){
            end += "\t";
            tabNum--;
        }
        return end;
    }

    /**
     * Repeat a character a number of times
     * @param reps The number of times to repeat it
     * @param ch The character to repeat
     * @return The repeated character string.
     */
    public static String charRep(int reps, char ch){
        String end = "";
        while(reps != 0){
            end += ch;
            reps--;
        }
        return end;
    }

    /**
     * Adjust the opacity of a color
     * @param in The color input
     * @return The new color with adjusted opacity.
     */
    public static Color adjustOpacity(Color in){
        int red = in.getRed();
        int blue = in.getBlue();
        int green = in.getGreen();
        int highest = 0;
        if(red > highest)
            highest = red;
        if(blue > highest)
            highest = blue;
        if(green > highest)
            highest = green;
        int alpha = (int) Math.ceil(4 * Math.sqrt(225 - highest)); // 20 if 200; 60 if 0; realistically, the lowest will be like 13 or someshit so very consistently between 20-60.
        return new Color(red, blue, green, alpha);
    }

    /**
     * Checks to see if an array contains a value f
     * @param n The array to check
     * @param f The value to look for
     * @param <T> The type
     * @return True if it contains, false if not.
     */
    public static <T> boolean contains(T[] n, T f){
        for(T i : n){
            if(i == f)
                return true;
        }
        return false;
    }

    /**
     * Looks for a tuple in a colleection of tuples.
     * @param tup The tuple to look for
     * @param in The collection of tuples to look in
     * @param <T> The type A
     * @param <R> The type B
     * @return The index of the tuple in collection in
     */
    public static <T,R> int findTuple(Tuple<T, R> tup, Collection<Tuple<T, R>> in){
        int count = 0;
        for(Tuple <T,R> i : in){
            if(tup.getB().equals(i.getB()) && tup.getA().equals(i.getA())){
                return count;
            }
            count++;
        }
        return -1;
    }

    /**
     * Search for one specific field of an item in a tuple.
     * @param search The tuple to look for
     * @param searchIn The tuple to look in
     * @param isA True if you're comparing the A term, false if you're comparing the B term.
     * @param <T> Type A
     * @param <R> Type B
     * @return The index of a tuple or -1 if cannot be found.
     */
    public static <T, R> int findTupleItem(Tuple<T, R> search, Collection<Tuple<T,R>> searchIn, boolean isA){
        int count = 0;
        for(Tuple<T, R> i : searchIn){
            if(isA){
                if(i.getA().equals(search.getA())){
                    return count;
                }
            }
            else{
                if(i.getB().equals(search.getB())){
                    return count;
                }
            }
            count++;
        }
        return -1;
    }
}
