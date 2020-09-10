package local.nix.input.checking.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class Producer{

    private ExecutorService es = Executors.newFixedThreadPool(1);
    private BlockingQueue<String> queue;


    public Producer (BlockingQueue<String> queue) {
        this.queue = queue;
    }


    private Runnable task = () -> {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                String input = br.readLine();
                queue.put(input);
                if ("quit".equals(input)) {
                    es.shutdown();
                    break;
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    };

    public void start() {

        es.execute(task);
    }
}
