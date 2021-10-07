package Misc;
/*
 * Brant Eckert, 11/8/20
 * Basic dice rolling class, has one method: roll
 */
public class Dice {
    /**
     * Rolls a dice wih a min and a max
     * @param min Minimum value
     * @param max Maximum value
     * @return The random integer value.
     */
    public static int roll(int min, int max){
        return (int)(Math.ceil(Math.random()*(max-min+1)) + min-1);
    }
}
