package blockchain;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockchainApp {

    public void run() {
        BlockChain blockChain = new BlockChain();
        ExecutorService executor = Executors.newCachedThreadPool();
        UserProvider.generateUsers(blockChain);

        List<Miner> miners = UserProvider.getMiners();
        List<Person> persons = UserProvider.getPersons();
        List<Company> companies = UserProvider.getCompanies();

        for (int i = 0; i < 5; i++) {
            try {
                executor.invokeAll(persons);
                executor.invokeAll(companies);
                Block block = executor.invokeAny(miners);
                blockChain.acceptNewBlock(block);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("ERROR > Exception occurred");
            }
        }

        executor.shutdownNow();
    }
}
