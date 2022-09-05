package blockchain;

import com.github.javafaker.Faker;

import java.util.Random;
import java.util.concurrent.Callable;

public class User implements Callable<Message> {
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
    public Message call() {
        Message message = null;
        if (blockChain.getNextBlockId() != 1) {
            if (random.nextInt(4) == 0) {
                try {
                    int messageId = blockChain.getNextMessageId();
                    message = new Message(messageId, faker.lorem().sentence(10), getName());
                    blockChain.addMessage(message);
                } catch (Exception e) {
                    System.out.println("ERROR > Could not create message" + e.getMessage());
                }
            }
        }

        return message;
    }
}
