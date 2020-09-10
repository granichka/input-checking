package local.nix.input.checking.consumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

public class Consumer {

    private ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    private BlockingQueue<String> queue;
    private ScheduledFuture<?> scheduledFuture;
    private File output = new File("output.txt");
    private String inputToCheck;


    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
        if (!output.exists()) {
            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable task = () -> {

        String elementFromQueue = null;
        try {
            elementFromQueue = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if("quit".equals(elementFromQueue)) {
            elementFromQueue = null;
            ses.shutdown();
        }

        if (elementFromQueue != null) {
            if (!elementFromQueue.equals(inputToCheck)) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {
                    bw.write(elementFromQueue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            inputToCheck = elementFromQueue;
        }

    };

    public void start() {

        scheduledFuture = ses.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }



}
