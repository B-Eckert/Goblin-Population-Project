package PeopleStuff;

import DataStructs.Tuple;
import Economy.Currency;
import Economy.Job;
import Economy.Wallet;
import Misc.*;
import TemporalStuff.Date;
import TemporalStuff.DeathEvent;
import TemporalStuff.PastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
/*
 * Brant Eckert, 11/8/2020
 * Creates an instance of a Goblin, the central focus of this entire program.
 */
public class Goblin {
    // constructors and shit

    /**
     * Makes a new goblin
     *
     * @param inHue  color param
     * @param inAge  age param
     * @param inMale true if male, false otherwise
     */
    public Goblin(String inName, String inSurname, Color inHue, int inAge, boolean inMale) {
        hue = inHue;
        age = inAge;
        male = inMale;
        personality = Personality.genRandom();
        name = inName;
        surname = inSurname;
    }

    /**
     * births a new goblin
     *
     * @param inHue  Color variable; must add up to 200
     * @param inAge  age
     * @param inMale true if male
     * @param inPA   reference to parent a
     * @param inPB   reference to parent b
     */
    public Goblin(String inName, String inSurname, Color inHue, int inAge, boolean inMale, Goblin inPA, Goblin inPB) {
        hue = inHue;
        age = inAge;
        male = inMale;
        personality = Personality.genRandom();
        parentA = inPA;
        parentB = inPB;
        name = inName;
        surname = inSurname;
    }

    // data
    private String name = "Defaultix";
    private String surname = "the Default Menace";
    private Personality personality = null;
    private Goblin parentA = null;
    private Goblin parentB = null;
    private Goblin partner = null;
    private String tombstoneText = "";
    private int age = 0;
    private final int lifespan = Dice.roll(90, 140);
    private boolean dead = false;
    private boolean employed = false;
    private boolean parent = false;
    private Color hue = new Color(75, 100, 25);
    private boolean male = true;
    private ArrayList<PastEvent> lifeevents = new ArrayList<>();
    private Wallet wallet = new Wallet(Currency.UNIVERSAL_CURRENCY);
    private Job job = null;
    // getters/setters

    /**
     * gets a pointer to parent a
     *
     * @return reference to parent a
     */
    public Goblin getParentA() {
        return parentA;
    }

    public boolean isEmployed(){
        return employed;
    }
    public void setEmployed(boolean em){
        employed = em;
    }
    /**
     * gets a pointer to parent b
     *
     * @return reference to parent b
     */
    public Goblin getParentB() {
        return parentB;
    }

    public String getName() {
        return name;
    }

    public void changeName(String n) {
        name = n;
    }

    public String getSurname() {
        return surname;
    }

    public void changeSurname(String n) {
        surname = n;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    /**
     * Gets color
     *
     * @return color
     */
    public Color getColor() {
        return hue;
    }

    /**
     * sets color
     *
     * @param n color to be set
     */
    public void setColor(Color n) {
        hue = n;
    }

    /**
     * acquires sex
     *
     * @return true if male, false otherwise
     */
    public boolean getSex() {
        return male;
    }

    /**
     * gets age
     *
     * @return age is just a number
     */
    public int getAge() {
        return age;
    }

    /**
     * increases age
     *
     * @param n age increaser.
     */
    public void increaseAge(int n, Date d) {
        age += n;
        if(age >= lifespan){
            die("advanced age", d); /// date is placeholder for actual overworld clock reference
        }

    }

    /**
     * acquires the personality field
     *
     * @return personality variable
     */
    public Personality getPersonality() {
        return personality;
    }

    public Wallet getWallet(){
        return wallet;
    }

    // economy stuff

    /**
     * Converts the goblin's money into a new currency
     * @param a The new currency.
     */
    public void newCurrency(Currency a){
        int converting = Wallet.convertCur(wallet.getCur(), wallet.checkBalance(), a);
        wallet = new Wallet(a, converting);
    }

    public void setJob(Job nJob){
        job = nJob;
    }
    // familial stuff

    /**
     * Recursive algorithm for generating ancestors
     * @param stop The number of generations to stop after
     * @return The array list of ancestors.
     */
    public ArrayList<Goblin> generateAncestors(int stop) {
        return generateAncestors(0, stop);
    }

    /**
     * The recursive algorithm for generating ancestors
     * @param n The current generation
     * @param stoppingPoint The stopping generation
     * @return An arraylist of ancestors.
     */
    public ArrayList<Goblin> generateAncestors(int n, int stoppingPoint){
        ArrayList<Goblin> ancestors = new ArrayList<>();
        ancestors.add(this);
        if(n != stoppingPoint) {
            if (parentA != null) {
                ancestors.addAll(parentA.generateAncestors(n + 1, stoppingPoint));
            }
            if (parentB != null) {
                ancestors.addAll(parentB.generateAncestors(n + 1, stoppingPoint));
            }
        }
        return ancestors;
    }

    /**
     * Getting the complete geneology of a specific goblin ; starts at 0
     * @param layer What layer of geneology to stop at.
     * @return A string representing the geneology of a goblin.
     */
    public String geneology(int layer){
        String end = getFullName() + "\n";
        if(parentA != null)
            end += General.charRep(layer, '-') + "Parent A, " + (layer+1) + ": " + parentA.geneology(layer+1);
        if(parentB != null)
            end += General.charRep(layer, '-') + "Parent B, " + (layer+1) + ": " + parentB.geneology(layer+1);
        return end;
    }

    /**
     * Getting the partial geneology of a specific goblin
     * @param layer The layer of geneology we're on
     * @param stoppingPoint The stopping point
     * @return Returns a string representing the geneology of a certain goblin.
     */
    public String geneology(int layer, int stoppingPoint){
        String end = getFullName() + "\n";
        if(stoppingPoint != 0) {
            if (parentA != null)
                end += General.charRep(layer, '-') + "Parent A, " + (layer + 1) + ": " + parentA.geneology(layer + 1, stoppingPoint - 1);
            if (parentB != null)
                end += General.charRep(layer, '-') + "Parent B, " + (layer + 1) + ": " + parentB.geneology(layer + 1, stoppingPoint - 1);
        }
        return end;
    }

    /**
     * Kill the goblin.
     * @param cause The cause of death
     * @param n The date of death
     * @return A string representing when and how the goblin died.
     */
    public String die(String cause, Date n){
        dead = true;
        tombstoneText = this.getFullName() + " died from " + cause + ".\n";
        int flavorText = Dice.roll(0, 30);
        lifeevents.add(new DeathEvent(this, n));
        if(flavorText <= 10){
            Tuple<String, Integer> maxPersonality = null;
            for(Tuple<String, Integer> i : this.getPersonality().getPersonalityValues()){
                if(maxPersonality == null)
                    maxPersonality = i;
                else {
                    if(maxPersonality.getB() < i.getB()){
                        maxPersonality = i;
                    }
                }
            }
            tombstoneText += "He was known for the greatest aspect of his character, his " + maxPersonality.getA() + ".";
        }
        else if(flavorText <= 20){
            // notable evevnts in the past
        }
        else if(flavorText <= 30){
            // parent, husband, etc
        }
        else{
            tombstoneText += "May he rest in piece.";
        }
        return tombstoneText;
    }

    /**
     * Checking if a goblin is dead.
     * @return True if dead, false if not
     */
    public boolean isDead(){
        return dead;
    }

    /**
     * Getting the tombstone text of a goblin.
     * @return If its alive, it returns an empty string. Otherwise, returns the full tombstone text.
     */
    public String getTombstoneText(){
        if(!dead)
            return "";
        else
            return tombstoneText;
    }

    // static methods

    /**
     * Creates a new generation of goblins from a previous generation of goblins.
     * All females and all males of the current generation have offspring together.
     * @param n The current generation of goblins.
     * @return The new generation of goblins.
     */
    public static ArrayList<Goblin> breedPopulation(ArrayList<Goblin> n){
        if(n.isEmpty())
            return new ArrayList<>();
        ArrayList<Goblin> result = new ArrayList<>();
        for(Goblin i : n){
            for(Goblin j : n){
                Goblin off = Goblin.intercourse(i, j);
                if(off != null){
                    result.add(off);
                }
            }
        }
        return result;
    }

    /**
     * Creates a new generation of goblins from a previous generation of goblins
     * but checks for inbreeding! if inbreeding is enabled.
     * @param n Current generation of goblins
     * @param inbreeding True if inbreeding is allowed, false if not.
     * @return Returns the new generation of goblins.
     */
    public static ArrayList<Goblin> breedPopulation(ArrayList<Goblin> n, boolean inbreeding){
        if(n.isEmpty())
            return new ArrayList<>();
        ArrayList<Goblin> result = new ArrayList<>();
        for(Goblin i : n){
            for(Goblin j : n){
                Goblin off = Goblin.intercourse(i, j, inbreeding);
                if(off != null){
                    result.add(off);
                }
            }
        }
        return result;
    }

    /**
     * Generats a new goblin with certain features; a color, a sex and a name and surname.
     * @return
     */
    public static Goblin randomGoblin(){
        // color:
        int green = Dice.roll(0, 200);
        int red = Dice.roll(0, (200 - green));
        int blue = 200 - green - red;
        Color col = General.adjustOpacity(new Color(green, red, blue));
        String name = Names.generateSensibleName();
        String surname = Names.generateSensibleName(6, 12);
        boolean sex = false;
        int n = Dice.roll(1, 7);
        if (n >= 3)
            sex = true;
        int age = Dice.roll(0, 90); // goblins have a planned lifespan of 100.

        return new Goblin(name, surname, col, age, sex, null, null);
    }

    public Goblin getPartner(){
        return partner;
    }

    /**
     * Gets two goblins married.
     * @param n The goblin to marry.
     */
    public void getMarried(Goblin n){
        if(partner != null) {
            partner.setMarried(null);
        }
        partner = n;
        n.setMarried(this);
    }

    /** Gets married but prevents incest.
     *
     * @param n Goblin to marry
     * @param incest Incest flag.
     */
    public void getMarried(Goblin n, boolean incest){
        if(incest)
            getMarried(n);
        else {
            for(Goblin i : this.generateAncestors(2)){
                for(Goblin j : n.generateAncestors(2)){
                    if(i.getFullName().equals(j.getFullName()))
                        return;
                }
            }
            getMarried(n);
        }
    }

    /**
     * Sets a goblin to be married to without respect to anything, including divorce.
     * @param n The goblin to marry.
     */
    private void setMarried(Goblin n){
        if(partner != null && n != null){
            partner.setMarried(null);
        }
        partner = n;
    }

    /**
     * Checks if a goblin is married
     * @return True if yes, false if not.
     */
    public boolean isMarried(){
        if(partner == null)
            return false;
        return true;
    }

    /**
     * Gets a goblin divorced with their partner.
     * @return The success of the divorce. Returns false if the goblin has no partner to divorce.
     */
    public boolean getDivorced(){
        if(partner != null) {
            partner.setMarried(null);
            partner = null;
            return true;
        }
        return false;
    }

    /**
     * Generates a goblin baby produced after intercourse.
     * @param a Goblin parent A
     * @param b Goblin parent B
     * @return returns the child of a and b if they are two different genders, or null if they are not.
     */
    public static Goblin intercourse(Goblin a, Goblin b){
        if(a.getSex() == b.getSex())
            return null;
        if(a.getSex())
            return offspring(a, b);
        else
            return offspring(b, a);
    }

    /**
     * Generates a goblin baby produced after intercourse with respect to inbreeding!
     * @param a Goblin parent A
     * @param b Goblin parent B
     * @param inbreeding Inbreeding flag (false for NO INBREEDING)
     * @return Returns the offspring of A and B
     */
    public static Goblin intercourse(Goblin a, Goblin b, boolean inbreeding){
        if (a.getSex() == b.getSex())
            return null;
        if(!inbreeding) {
            if(a.incestCheck(b, 2))
                return null;
        }
        if (a.getSex())
            return offspring(a, b);
        else
            return offspring(b, a);
    }

    /**
     * produces an offspring from two goblins
     * note: does not check if they are of two different sexes
     * (two dudes can create children without caller checking)
     *
     * @param a parent a
     * @param b parent b
     * @return fully formed child at age 0
     */
    public static Goblin offspring(Goblin a, Goblin b) {
        int rd = a.getColor().getRed() + b.getColor().getRed();
        int gn = a.getColor().getGreen() + b.getColor().getGreen();
        int bl = a.getColor().getBlue() + b.getColor().getBlue();

        rd /= 2;
        gn /= 2;
        bl /= 2;
        boolean nMale = false;
        int offTwoHun = 200 - (rd + gn + bl);
        gn += offTwoHun;
        int n = Dice.roll(1, 7);
        if (n >= 3)
            nMale = true;
        String surname = a.getSurname();
        int nameCheck = Dice.roll(0, 100);
        if (nameCheck <= 5)
            surname = Names.generateSensibleName(6, 12);
        else if(nameCheck <= 15)
            surname = b.getSurname();
        return new Goblin(Names.generateSensibleName(), surname, General.adjustOpacity(new Color(rd, gn, bl)), 0, nMale, a, b);
    }

    /**
     * Checks if two goblins are related to a certain point.
     * @param other The other goblin
     * @param layers The layers to go back to see if they're related
     * @return True if intercourse is incest, false if not.
     */
    public boolean incestCheck(Goblin other, int layers){
        ArrayList<Goblin> gene1 = this.generateAncestors(layers);
        ArrayList<Goblin> gene2 = other.generateAncestors(layers);
        for(Goblin i : gene1) {
            for(Goblin j : gene2){
                if(i.equals(j)){
                    return true;
                }
            }
        }
        return false;
    }

    // system methods

    /**
     * Checks to see if two goblin objects equal each other
     * @param other The other goblin.
     * @return True if equal, false if not.
     */
    public boolean equals(Object other){
        if(other instanceof Goblin){
            Goblin nOther = (Goblin) other;
            if(nOther.getFullName().equals(this.getFullName()) &&
               nOther.getSex() == this.getSex() &&
                nOther.getPersonality().toString().equals(this.getPersonality().toString()) &&
               nOther.getColor().equals(this.getColor())){
                return true;
            }
        }
        return false;
    }

    /**
     * Represents a goblin as a string.
     * @return The goblin formatted as a string.
     */
    public String toString() {
        String end = getFullName() + "\n-----------" +
                "\nAge: " + age +
                "\nSex: ";
        if(male)
            end += "male";
        else
            end += "female";
        end += "\nGeneology:\n" + this.geneology(0, 3) +
                "Personality: " + getPersonality().toString() + "\n" +
                "Color: { Red:" + this.getColor().getRed() + " Green:" + this.getColor().getGreen() + " Blue:" + this.getColor().getBlue() + " }";
        return end;
    }
}
