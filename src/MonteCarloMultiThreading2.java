import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Philippe Rémy
 * Date: 5/28/13
 * Time: 8:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonteCarloMultiThreading2 {

    private static final int ITERATIONS_COUNT = 1000000000;

    private static final int CORES_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int STEP = ITERATIONS_COUNT / CORES_COUNT;

    private static int[] hits = new int[CORES_COUNT];

    private static class RunTask implements Runnable {

        private int id = 0;

        @Override
        public void run() {
            System.out.println("Running task with id " + id);
            double x, y;

            for(int i=0; i<STEP; i++)
            {
                x = Math.random();
                y = Math.random();

                if(x*x + y*y <= 1)
                {
                    //System.out.println("[" + id + "] : " + hits[id]);
                    hits[id]++;
                }
            }

        }

        public void setId(int i) {
            this.id = i;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long n1 = System.currentTimeMillis();
        System.out.println("Monte Carlo - Multi Threaded Version");
        System.out.println("Approximation of PI");

        System.out.println("Detecting " + CORES_COUNT + " processors available");
        System.out.println("Step is " + STEP);

        ExecutorService executorService = Executors.newFixedThreadPool(CORES_COUNT);

        for(int i=0; i<CORES_COUNT; i++)
        {
            RunTask runTask = new RunTask();
            runTask.setId(i);
            executorService.execute(runTask);
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        /**
         * hits / limit = PI / 4.
         */

        int hitsCount = 0;
        for(int i=0; i<CORES_COUNT; i++)
        {
            hitsCount += hits[i];
        }

        double PI_estimation = 4*(((double)hitsCount)/ITERATIONS_COUNT);

        System.out.println("PI value = " + Math.PI);
        System.out.println("PI estimation = " + PI_estimation);

        System.out.println(System.currentTimeMillis() - n1  + " ms");

    }

}
