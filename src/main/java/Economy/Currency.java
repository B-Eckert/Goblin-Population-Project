package Economy;

/*
 * Brant Eckert, 12/19/20
 * Definition of a currency.
 */
public class Currency {
    public static final Currency UNIVERSAL_CURRENCY = new Currency("Default Bucks", 1, "A boring goblin"); // the universal currency to act as a baseline.
    private String name = "";
    private double relativeEval = 1; // thought experiment; if this = 2, then this = 2ubucks, so divide then multiply.
    private String picture = "";
    public Currency(String name){
        this.name = name;
    }
    public Currency(String name, double inEv){
        this(name);
        relativeEval = inEv;
    }
    public Currency(String name, String picture){
        this(name);
        this.picture = picture;
    }
    public Currency(String name, double inEv, String picture){
        this(name, inEv);
        this.picture = picture;
    }

    public String getName(){
        return name;
    }
    public String getPicture(){
        return picture;
    }
    public double getRelativeEval(){
        return relativeEval;
    }
    public void setRelativeEval(double inEv){
        relativeEval = inEv;
    }
}
