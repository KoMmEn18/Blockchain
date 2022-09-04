package blockchain;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockchainApp {

    public void run() {
        BlockChain blockChain = new BlockChain();
        Faker faker = new Faker();

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Miner> miners = Stream.generate(() -> new Miner(blockChain))
                .limit(10)
                .collect(Collectors.toList());

        List<User> users = Stream.generate(() -> new User(faker.name().firstName(), blockChain, faker))
                .limit(5)
                .collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            try {
                executor.invokeAll(users);
                Block block = executor.invokeAny(miners);
                blockChain.acceptNewBlock(block);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("ERROR > Exception occurred");
            }
        }

        executor.shutdown();
    }
}
