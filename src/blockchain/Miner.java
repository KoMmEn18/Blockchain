package blockchain;

import java.util.concurrent.Callable;

public class Miner extends User implements Callable<Block> {

    private static int nextId = 1;

    public Miner(BlockChain blockChain) {
        super("miner" + nextId++, blockChain);
    }

    public String getName() {
        return name;
    }

    @Override
    public Block call() {
        generateRandomTransaction(5);
        return new Block(blockChain.getNextBlockId(),
                getName(),
                blockChain.getHashOfLastBlock(),
                blockChain.getLeadingZeros());
    }
}
