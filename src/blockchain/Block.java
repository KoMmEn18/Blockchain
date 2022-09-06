package blockchain;

import blockchain.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Block {

    private final int id;
    private final String minerName;
    private final long timeStamp;
    private final String previousBlockHash;
    private final long secondsToGenerate;
    private List<Transaction> data;
    private int magicNumber;

    public Block(int id, String minerName, String previousBlockHash, int leadingZeros) {
        Date startDate = new Date();
        this.id = id;
        this.minerName = minerName;
        this.timeStamp = new Date().getTime();
        this.previousBlockHash = previousBlockHash;
        setMagicNumber(leadingZeros);
        this.secondsToGenerate = (new Date().getTime() - startDate.getTime()) / 1000;
    }

    public int getId() {
        return id;
    }

    public String getMinerName() {
        return minerName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public long getSecondsToGenerate() {
        return secondsToGenerate;
    }

    public String getBlockHash() {
        return StringUtil.sha256(getMinerName() + getId() + getTimeStamp() + getMagicNumber() + getPreviousBlockHash());
    }

    public void setData(List<Transaction> data) {
        this.data = data;
    }

    public List<Transaction> getData() {
        return data;
    }

    public int getMaxMessageId() {
        return getData().stream().map(Transaction::getId).max(Integer::compareTo).orElse(-1);
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");
        return String.join(lineSeparator,
                "Block:",
                "Created by: " + getMinerName(),
                getMinerName() + " gets 100 VC",
                "Id: " + getId(),
                "Timestamp: " + getTimeStamp(),
                "Magic number: " + getMagicNumber(),
                "Hash of the previous block:",
                getPreviousBlockHash(),
                "Hash of the block:",
                getBlockHash(),
                "Block data: " + (getData().isEmpty() ? "no messages" : lineSeparator + getData().stream()
                        .map(s -> s.getSender() + " sent " + s.getValue() + " VC to " + s.getReceiver())
                        .collect(Collectors.joining(lineSeparator))),
                "Block was generating for " + getSecondsToGenerate() + " seconds");
    }

    private void setMagicNumber(int leadingZeros) {
        int magic = 1;
        do {
            this.magicNumber = magic++;
        } while (!(getBlockHash().startsWith("0".repeat(leadingZeros)) && getBlockHash().charAt(leadingZeros) != '0'));
    }
}
