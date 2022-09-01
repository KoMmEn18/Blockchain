package blockchain;

import blockchain.util.SerializationUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain implements Serializable {

    private ArrayList<Block> blockChain = new ArrayList<>();
    private int leadingZeros;
    private static final long serialVersionUID = 1L;

    public BlockChain(int leadingZeros) {
        this.leadingZeros = leadingZeros;
        //loadBlockChain();
    }

    public void generateNewBlock() {
        String hashOfPreviousBlock = "0";
        if (!blockChain.isEmpty()) {
            hashOfPreviousBlock = blockChain.get(blockChain.size() - 1).getBlockHash();
        }
        Block block = new Block(hashOfPreviousBlock, leadingZeros);
        blockChain.add(block);
        SerializationUtil.serialize(blockChain);
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

    private void loadBlockChain() {
        var blockChain = (ArrayList<Block>) SerializationUtil.deserialize();
        if (blockChain != null) {
            this.blockChain = blockChain;
            if (this.validate()) {
                Block.setNextId(blockChain.get(blockChain.size() - 1).getId() + 1);
            } else {
                this.blockChain.clear();
            }
        }
    }
}
