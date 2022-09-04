package blockchain;

import com.github.javafaker.Faker;

import java.util.Random;
import java.util.concurrent.Callable;

public class User implements Callable<String> {
    private String name;
    private BlockChain blockChain;
    private Faker faker;
    private static Random random = new Random();

    public User(String name, BlockChain blockChain, Faker faker) {
        this.name = name;
        this.blockChain = blockChain;
        this.faker = faker;
    }

    public String getName() {
        return name;
    }

    @Override
    public String call() {
        String message = null;
        if (blockChain.getNextBlockId() != 1) {
            if (random.nextInt(4) == 0) {
                message = faker.lorem().sentence(10);
                blockChain.addMessage(getName() + ": " + message);
            }
        }

        return message;
    }
}
