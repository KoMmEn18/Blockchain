package blockchain;

import java.util.concurrent.Callable;

public class Company extends User implements Callable<Transaction> {

    public Company(String name, BlockChain blockChain) {
        super(name, blockChain);
    }

    @Override
    public Transaction call() {
        return generateRandomTransaction(6);
    }
}
