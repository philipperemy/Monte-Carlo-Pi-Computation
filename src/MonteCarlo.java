/**
 * Created with IntelliJ IDEA.
 * User: Philippe Rémy
 * Date: 5/27/13
 * Time: 10:23 PM
 */
public class MonteCarlo {

    public static void main(String[] args)
    {
        long n1 = System.currentTimeMillis();
        System.out.println("Monte Carlo - Mono Threaded Version");
        System.out.println("Approximation of PI");

        final int ITERATIONS_COUNT = 1000000000;
        int hits = 0;
        double x, y;

        for(int i=0; i<ITERATIONS_COUNT; i++)
        {
            x = Math.random();
            y = Math.random();

            if(x*x + y*y <= 1)
            {
                //System.out.println("[" + "0" + "] : " + hits);
                hits++;
            }
        }

        /**
         * hits / limit = PI / 4.
         */

        double PI_estimation = 4*(((double)hits)/ITERATIONS_COUNT);

        System.out.println("PI value = " + Math.PI);
        System.out.println("PI estimation = " + PI_estimation);

        System.out.println(System.currentTimeMillis() - n1  + " ms");

    }

}
