package blockchain;

import java.util.Objects;
import java.util.Random;

public class User {
    protected String name;
    protected BlockChain blockChain;
    protected static Random random = new Random();

    public User(String name, BlockChain blockChain) {
        this.name = name;
        this.blockChain = blockChain;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        return 31 * result;
    }

    protected Transaction generateRandomTransaction(int randBound) {
        Transaction transaction = null;
        if (blockChain.getNextBlockId() != 1 && random.nextInt(randBound) == 0) {
            int balance = blockChain.getUserBalance(getName());
            if (balance > 0) {
                int randomTransactionValue = random.nextInt(balance) + 1;
                try {
                    int transactionId = blockChain.getNextTransactionId();
                    transaction = new Transaction(transactionId, randomTransactionValue, getName(), UserProvider.getRandomUser().getName());
                    blockChain.addTransaction(transaction);
                } catch (Exception e) {
                    System.out.println("ERROR > Could not create transaction");
                }
            }
        }

        return transaction;
    }
}
