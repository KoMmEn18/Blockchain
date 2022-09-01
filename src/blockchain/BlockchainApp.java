package blockchain;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockchainApp {

    public void run() {
        BlockChain blockChain = new BlockChain();
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Miner> miners = Stream.generate(() -> new Miner(blockChain))
                .limit(10)
                .collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            try {
                Block block = executor.invokeAny(miners);
                blockChain.acceptNewBlock(block);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("ERROR > Exception occurred");
            }
        }

        executor.shutdown();
    }
}
