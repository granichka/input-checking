package local.nix.input.checking;

import local.nix.input.checking.consumer.Consumer;
import local.nix.input.checking.producer.Producer;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        new Thread(new Producer(queue)).start();
        new Consumer(queue).start();




    }


}
