package blockchain;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockChain {

    private ArrayList<Block> blockChain = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private int leadingZeros = 0;
    private static int transactionId = 1;

    public synchronized boolean acceptNewBlock(Block block) {
        Block previousBlock = blockChain.isEmpty() ? null : blockChain.get(blockChain.size() - 1);
        if (getHashOfLastBlock().equals(block.getPreviousBlockHash())
                && block.getBlockHash().startsWith("0".repeat(leadingZeros))
                && (previousBlock == null || transactions.stream().allMatch(m -> m.getId() > previousBlock.getMaxMessageId()))) {
            block.setData(transactions);
            transactions = new ArrayList<>();
            blockChain.add(block);
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

    public synchronized int getUserBalance(String userName) {
        int initialBalance = 100;

        initialBalance += blockChain.stream()
                .mapToInt(b -> b.getMinerName().equals(userName) ? 100 : 0)
                .sum();

        initialBalance += blockChain.stream()
                .mapToInt(b -> b.getData().stream()
                        .mapToInt(t -> {
                            if (t.getReceiver().equals(userName)) {
                                return t.getValue();
                            } else if (t.getSender().equals(userName)) {
                                return -t.getValue();
                            }
                            return 0;
                        }).sum())
                .sum();

        return initialBalance;
    }

    public synchronized void addTransaction(Transaction transaction) {
        if (getNextBlockId() != 1 && getUserBalance(transaction.getSender()) > transaction.getValue()) {
            transactions.add(transaction);
        }
    }

    public synchronized int getNextTransactionId() {
        return transactionId++;
    }

    public boolean validate() {
        VerifyMessage verifyMessage = new VerifyMessage();

        return blockChain.stream()
                .skip(1)
                .allMatch(block -> block.getPreviousBlockHash()
                        .equals(blockChain.get(block.getId() - 2).getBlockHash()))
                && blockChain.stream().allMatch(s -> s.getData().stream().allMatch(t -> {
                    try {
                        return verifyMessage.verifySignature(t);
                    } catch (Exception e) {
                        System.out.println("ERROR > Could not verify message");
                        return false;
                    }
                }));
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
}
