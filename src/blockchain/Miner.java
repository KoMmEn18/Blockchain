package blockchain;

import java.util.concurrent.Callable;

public class Miner implements Callable<Block> {

    private int id;
    private BlockChain blockChain;

    private static int nextId = 1;

    public Miner(BlockChain blockChain) {
        this.blockChain = blockChain;
        this.id = nextId++;
    }

    public int getId() {
        return id;
    }

    @Override
    public Block call() {
        return new Block(blockChain.getNextBlockId(),
                getId(),
                blockChain.getHashOfLastBlock(),
                blockChain.getLeadingZeros());
    }
}
