package Economy;

import java.util.ArrayList;

/*
 * Brant Eckert, 12/19/20
 * Goblin business, has a number of accounts, job offerings and filled jobs.
 * The goblin economy is VERY UNFINISHED.
 */
public class Business {
    private Wallet accounts = new Wallet(Currency.UNIVERSAL_CURRENCY);
    private ArrayList<Job> offerings = new ArrayList<>();
    private ArrayList<Job> types = new ArrayList<>();

    /**
     * Instantiates a new Business using a currency cur, a starting amount input, and a Job ceo
     * @param cur Currency the business uses
     * @param input Starting funds
     * @param ceo The job of the CEO
     */
    public Business(Currency cur, int input, Job ceo){
        accounts = new Wallet(cur, input);
        offerings.add(ceo);
        types.add(ceo.clone());
    }
    /**
     * Instantiates a new Business using a currency cur, a starting amount input, and a Job ceo
     * @param cur Currency the business uses
     * @param input Starting funds
     * @param ceo The job of the CEO
     * @param inTypes The starting types of jobs offered
     * @param eachOf The number of jobs offered of each type.
     */
    public Business(Currency cur, int input, Job ceo, Job[] inTypes, int[] eachOf){
        this(cur, input, ceo);
        for(int i = 0; i < inTypes.length && i < eachOf.length; i++){
            types.add(inTypes[i]);
            int j = eachOf[i];
            while(j > 0){
                offerings.add(inTypes[i].clone());
                j--;
            }
        }
    }

    /**
     * Getter for the business finances.
     * @return The Wallet of the business.
     */
    public Wallet getAccounts(){
        return accounts;
    }

    /**
     * Remove a certain job
     * @param n Job to remove.
     */
    public void remove(Job n){
        n.fire();
        offerings.remove(n);
    }

    /**
     * Remove one of the many jobs on offer.
     * @param n The job to remove.
     */
    public void removeOne(Job n){
        for(Job i : offerings){
            if(i.equals(n)){
                i.fire();
                offerings.remove(i);
                return;
            }
        }
    }
}
