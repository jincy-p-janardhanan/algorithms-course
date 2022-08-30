import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord{
    public static void main(String[] args) {
        int i = 0;
        String champion="", s;
        while(!StdIn.isEmpty()){
            s = StdIn.readString();
            if(StdRandom.bernoulli(1/(double)(++i))){
                champion = s;
            }
        }
        StdOut.println(champion);
    }
}