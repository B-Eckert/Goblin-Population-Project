package DataStructs;

/*
 * Brant Eckert, 11/8/20
 * Basic tuple data structure. Gets and sets A and B.
 */
public class Tuple <T, R> {
    T a;
    R b;
    public Tuple(T inA, R inB){
        a = inA;
        b = inB;
    }
    public T getA(){
        return a;
    }
    public void setA(T n){
        a = n;
    }
    public R getB(){
        return b;
    }
    public void setB(R n){
        b = n;
    }
}
