import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Philippe Rémy
 * Date: 5/27/13
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonteCarloMultiThreading {

    private static AtomicInteger hits = new AtomicInteger(0);

    private static final int ITERATIONS_COUNT = Integer.MAX_VALUE;

    private static class RunTask implements Runnable {

        private int id = 0;

        @Override
        public void run() {
            System.out.println("Running task with id " + id);
            double x, y;

            for(int i=0; i<ITERATIONS_COUNT; i++)
            {
                x = Math.random();
                y = Math.random();

                if(x*x + y*y <= 1)
                {
                    System.out.println("[" + id + "] : " + hits.get());
                    hits.incrementAndGet();
                }
            }

        }

        public void setId(int i) {
            this.id = i;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Monte Carlo - Mono Threaded Version");
        System.out.println("Approximation of PI");

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Detecting " + availableProcessors + " processors available");

        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);

        for(int i=0; i<availableProcessors; i++)
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

        double PI_estimation = 4*(((double)hits.get())/ITERATIONS_COUNT);
        PI_estimation /= availableProcessors;

        System.out.println("PI value = " + Math.PI);
        System.out.println("PI estimation = " + PI_estimation);

    }

}
