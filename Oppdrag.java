package sample;

import javafx.concurrent.Task;

public class Oppdrag extends Task<Integer> {

    @Override
    public Integer call() throws InterruptedException {


            Thread.sleep(300);

            for (int i = 0; i < 10; i++) {
                updateProgress(i + 1, 10);
                Thread.sleep(500);
                if (isCancelled()) return i;
            }

        return 10; //returnere 100%
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        updateMessage("avsluttet!");
        return super.cancel(mayInterruptIfRunning);
    }


}
