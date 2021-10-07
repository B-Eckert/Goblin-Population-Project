package PeopleStuff;

import Misc.Dice;

/*
 * Brant Eckert, 11/8/20
 * Generates random names with consonants that make sense, vowels that make sense and random characters.
 */
public class Names {
    public static final char[] CONSONANTS = new char[] {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q',
                                        'r', 's', 't','v','w','x','y','z'};
    public static final char[] VOWELS = new char[] {'a', 'e', 'i', 'o', 'u'};

    public static final String[] CONSONANTS_THAT_MAKE_SENSE = new String[] {
            "b", "br", "by", "bv", "bl",
            "c", "ch", "cr",  "cn", "cl",
            "d", "dr", "dj", "dl", "dv", "dy",
            "f", "fr", "fl", "fv", "fy",
            "g", "gr", "gl", "gy",
            "h", "hr", "hy",
            "j", "jr", "jl", "jy",
            "k", "kr", "kl", "kh", "ky",
            "l", "lt", "lx",
            "m", "mr", "my",
            "n", "nr", "ny",
            "p", "pr", "ph",
            "q",
            "r", "ry",
            "s", "sr", "sl", "sy", "sp",
            "t", "tr", "th", "ty", "tl",
            "v", "vr",
            "w", "wr",
            "x",
            "y",
            "z", "zr"
    };

    public static final String[] VOWELS_THAT_MAKE_SENSE = new String[] {
            "a", "ae", "ai", "ao", "au",
            "e", "ea", "ee", "ei", "eo", "eu",
            "i", "ia", "ie", "io", "iu",
            "o", "oa", "oi", "oo", "ou", "oe",
            "u", "ua", "ui", "ue"
    };

    /**
     * Generates a name out of the random set of characters
     * @return A name that may or may not be pronounceable
     */
    public static String generateName(){
        int n = Dice.roll(1, 2);
        if(n == 1)
            return generateRandomName();
        else
            return generateSensibleName();
    }

    /**
     * Generates a random name of length l
     * @param length l
     * @return A random name that probably wont make sense.
     */
    public static String generateName(int length){
        return generateName(length, length);
    }

    /**
     * Generate a name that may or may not make sense between min and max length
     * @param minLength min
     * @param maxLength max
     * @return The name that may or may not make sense.
     */
    public static String generateName(int minLength, int maxLength){
        int n = Dice.roll(1, 2);
        if(n == 1)
            return generateRandomName(minLength, maxLength);
        else
            return generateSensibleName(minLength, maxLength);
    }

    /**
     * Generate a purely random name between 4-8 characters
     * @return The random name.
     */
    public static String generateRandomName(){
        return generateRandomName(4, 8);
    }

    /**
     * Generate a random name of length l
     * @param length l
     * @return The name
     */
    public static String generateRandomName(int length){
        return generateRandomName(length, length);
    }

    /**
     * The function controlling the generation of a random name.
     * @param minLength Minimum name length
     * @param maxLength Maximum name length
     * @return Name
     */
    public static String generateRandomName(int minLength, int maxLength){
        String name = "";
        if(minLength > maxLength || (minLength == 0 || maxLength == 0))
            return name;
        int length = Dice.roll(minLength, maxLength);
        int curLength = 0;
        boolean consLatch = false;
        if(Dice.roll(0,1) == 1)
            consLatch = true;
        while(curLength < length){
            if(consLatch){
                if(Dice.roll(1,2) == 2 && (length - curLength) >= 2){
                    name += CONSONANTS[Dice.roll(0, CONSONANTS.length-1)];
                    curLength++;
                }
                name += CONSONANTS[Dice.roll(0, CONSONANTS.length-1)];
                curLength++;
                consLatch = false;
            }
            else {
                if(Dice.roll(1,2) == 2 && (length - curLength) >= 2){
                    name += VOWELS[Dice.roll(0, VOWELS.length-1)];
                    curLength++;
                }
                name += VOWELS[Dice.roll(0, VOWELS.length-1)];
                curLength++;
                consLatch = true;
            }
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
// ---------- SENSIBLE NAME FUNCTIONS -----------------

    /**
     * Generate a name that may make sense between 4 & 8 characters
     * @return The sensible name.
     */
    public static String generateSensibleName(){
        return generateSensibleName(4, 8);
    }

    /**
     * Generate a name that may make sense of length l
     * @param length l
     * @return A sensible name.
     */
    public static String generateSensibleName(int length){
        return generateSensibleName(length, length);
    }

    /**
     * Generate a sensible name between length min and max by flipping between consonant and vowel strings randomly.
     * @param minLength min
     * @param maxLength max
     * @return The "sensible" name.
     */
    public static String generateSensibleName(int minLength, int maxLength){
        String name = "";
        if(minLength > maxLength || (minLength == 0 || maxLength == 0))
            return name;
        int length = Dice.roll(minLength, maxLength);
        boolean consLatch = false;
        if(Dice.roll(0,1) == 1)
            consLatch = true;
        while(name.length() < length){
            if(consLatch){
                name += CONSONANTS_THAT_MAKE_SENSE[Dice.roll(0, CONSONANTS_THAT_MAKE_SENSE.length-1)];
                consLatch = false;
            }
            else {
                name += VOWELS_THAT_MAKE_SENSE[Dice.roll(0, VOWELS_THAT_MAKE_SENSE.length-1)];
                consLatch = true;
            }
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
