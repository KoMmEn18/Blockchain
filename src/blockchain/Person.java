package blockchain;

import java.util.concurrent.Callable;

public class Person extends User implements Callable<Transaction> {

    public Person(String name, BlockChain blockChain) {
        super(name, blockChain);
    }

    @Override
    public Transaction call() {
        return generateRandomTransaction(4);
    }
}
