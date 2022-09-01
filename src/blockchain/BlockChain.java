package blockchain;

import blockchain.util.SerializationUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain implements Serializable {

    private ArrayList<Block> blockChain = new ArrayList<>();
    private int leadingZeros = 0;
    private static final long serialVersionUID = 1L;

    public BlockChain() {
        //loadBlockChain();
    }

    public synchronized boolean acceptNewBlock(Block block) {
        if (getHashOfLastBlock().equals(block.getPreviousBlockHash())
                && block.getBlockHash().startsWith("0".repeat(leadingZeros))) {
            blockChain.add(block);
            //SerializationUtil.serialize(blockChain);
            System.out.println(block);
            manageLeadingZeros(block);

            return true;
        }

        return false;
    }

    public synchronized String getHashOfLastBlock() {
        String hashOfLastBlock = "0";
        if (!blockChain.isEmpty()) {
            hashOfLastBlock = blockChain.get(blockChain.size() - 1).getBlockHash();
        }

        return hashOfLastBlock;
    }

    public synchronized int getLeadingZeros() {
        return leadingZeros;
    }

    public synchronized int getNextBlockId() {
        return blockChain.size() + 1;
    }

    public boolean validate() {
        return blockChain.stream()
                .skip(1)
                .allMatch(block -> block.getPreviousBlockHash()
                        .equals(blockChain.get(block.getId() - 2).getBlockHash()));
    }

    public void printBlockChain() {
        System.out.println(blockChain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n\n")));
    }

    private void manageLeadingZeros(Block block) {
        if (block.getSecondsToGenerate() < 10) {
            leadingZeros++;
            System.out.println("N was increased to " + leadingZeros);
        } else if (block.getSecondsToGenerate() > 60) {
            leadingZeros--;
            System.out.println("N was decreased by 1");
        } else {
            System.out.println("N stays the same");
        }
        System.out.println();
    }

    private void loadBlockChain() {
        var blockChain = (ArrayList<Block>) SerializationUtil.deserialize();
        if (blockChain != null) {
            this.blockChain = blockChain;
            if (!this.validate()) {
                this.blockChain.clear();
            }
        }
    }
}
