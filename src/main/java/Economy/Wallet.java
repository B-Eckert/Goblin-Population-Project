package Economy;

/*
 * Brant Eckert 12/19/20
 * Wallet, or account, of an entity.
 */

public class Wallet {
    private Currency cur = null;
    private int balance = 0;
    public Wallet(Currency inCur){
        cur = inCur;
    }
    public Wallet(Currency inCur, int initBalance){
        this(inCur);
        balance = initBalance;
    }

    public void deposit(int dep){
        balance += dep;
    }
    public int withdraw(int quant){
        balance -= quant;
        return quant;
    }
    public int checkBalance(){
        return balance;
    }

    public Currency getCur(){
        return cur;
    }

    /**
     * Convert from one currency to another
     * @param a Currency converting from
     * @param quant The quantity you are converting.
     * @param b The second currency.
     * @return The converted amount.
     */
    public static int convertCur(Currency a, int quant, Currency b){
        double quantDouble = (double) quant;
        quantDouble /= a.getRelativeEval();
        quantDouble *= b.getRelativeEval();
        quantDouble = Math.floor(quantDouble);
        return (int) quantDouble;
    }
}
